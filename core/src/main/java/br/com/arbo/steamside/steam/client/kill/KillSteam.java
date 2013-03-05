package br.com.arbo.steamside.steam.client.kill;

import org.apache.commons.lang3.SystemUtils;
import org.jvnet.winp.WinProcess;

public class KillSteam {

	public static void kill() {
		final String myusername = SystemUtils.USER_NAME;

		WinProcess.enableDebugPrivilege();
		final WinProcess steam_exe;
		try {
			steam_exe = FindSteam.find_steam_exe(myusername);
		} catch (final NotFound e) {
			return;
		}
		steam_exe.killRecursively();
	}

}
