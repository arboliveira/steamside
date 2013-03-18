package br.com.arbo.processes.seek.windows;

import java.util.TreeMap;

import org.jvnet.winp.WinProcess;
import org.jvnet.winp.WinpException;

import br.com.arbo.processes.seek.NotFound;

class FindWith_winp {

	private final String myusername;
	private final String executable;

	FindWith_winp(final String executable, final String myusername) {
		this.executable = executable;
		this.myusername = myusername;
	}

	WinProcess find() throws NotFound {
		final WinProcess exe = find_exe();
		if (isMine(exe)) throw new NotFound();
		return exe;
	}

	private WinProcess find_exe() throws NotFound {
		for (final WinProcess p : WinProcess.all())
			if (isExe(p)) return p;
		throw new NotFound();
	}

	private boolean isExe(final WinProcess p) {
		final String commandLine;
		try {
			commandLine = p.getCommandLine();
		} catch (final WinpException e) {
			final int code = e.getWin32ErrorCode();
			if (code == 87 || code == 5 || code == 299) return false;
			throw e;
		}
		return commandLine.contains(executable);
	}

	boolean isMine(final WinProcess exe) {
		final TreeMap<String, String> env =
				exe.getEnvironmentVariables();
		final String steamusername = env.get("USERNAME");
		return steamusername.equals(myusername);
	}

}
