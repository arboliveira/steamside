package br.com.arbo.steamside.steam.client.kill;

import java.util.TreeMap;

import org.jvnet.winp.WinProcess;
import org.jvnet.winp.WinpException;

class FindWith_winp {

	private final String myusername;

	public FindWith_winp(final String myusername) {
		this.myusername = myusername;
	}

	WinProcess find() {
		final WinProcess exe = find_steam_exe();
		if (isMine(exe)) throw new NotFound();
		return exe;
	}

	private static WinProcess find_steam_exe() {
		for (final WinProcess p : WinProcess.all())
			if (isSteam(p)) return p;
		throw new NotFound();
	}

	private static boolean isSteam(final WinProcess p) {
		final String commandLine;
		try {
			commandLine = p.getCommandLine();
		} catch (final WinpException e) {
			final int code = e.getWin32ErrorCode();
			if (code == 87 || code == 5 || code == 299) return false;
			throw e;
		}
		return commandLine.contains("steam.exe");
	}

	boolean isMine(final WinProcess exe) {
		final TreeMap<String, String> env =
				exe.getEnvironmentVariables();
		final String steamusername = env.get("USERNAME");
		return steamusername.equals(myusername);
	}

}
