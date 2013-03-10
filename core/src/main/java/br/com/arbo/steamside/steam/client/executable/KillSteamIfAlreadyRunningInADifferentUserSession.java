package br.com.arbo.steamside.steam.client.executable;

import org.apache.commons.lang3.SystemUtils;
import org.jvnet.winp.WinProcess;

import br.com.arbo.steamside.opersys.username.User;

public class KillSteamIfAlreadyRunningInADifferentUserSession {

	private final User username;

	public KillSteamIfAlreadyRunningInADifferentUserSession(
			final User username) {
		this.username = username;
	}

	@SuppressWarnings("static-method")
	public void confirm() {
		if (!SystemUtils.IS_OS_WINDOWS) return;

		final String myusername = username.username();

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
