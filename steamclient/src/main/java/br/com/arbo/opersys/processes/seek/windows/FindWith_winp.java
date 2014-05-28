package br.com.arbo.opersys.processes.seek.windows;

import java.util.TreeMap;

import org.jvnet.winp.WinProcess;
import org.jvnet.winp.WinpException;

import br.com.arbo.opersys.processes.seek.Criteria;
import br.com.arbo.opersys.processes.seek.NotFound;
import br.com.arbo.opersys.processes.seek.UsernameNot;

class FindWith_winp {

	private final Criteria criteria;

	FindWith_winp(final Criteria criteria) {
		this.criteria = criteria;
	}

	WinProcess find() throws NotFound {
		final WinProcess exe = find_exe();
		filterUsernameNot(exe);
		return exe;
	}

	private void filterUsernameNot(final WinProcess exe) throws NotFound {
		final UsernameNot unwanted = criteria.usernameNot;
		if (unwanted == null) return;
		if (usernameOf(exe).equals(unwanted.username))
			throw new NotFound();
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
		return commandLine.contains(criteria.executable);
	}

	private static String usernameOf(final WinProcess exe) {
		final TreeMap<String, String> env =
				exe.getEnvironmentVariables();
		return env.get("USERNAME");
	}

}
