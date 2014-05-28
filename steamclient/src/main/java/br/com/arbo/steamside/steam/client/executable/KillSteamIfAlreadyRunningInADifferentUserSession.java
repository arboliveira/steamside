package br.com.arbo.steamside.steam.client.executable;

import org.apache.commons.lang3.SystemUtils;
import org.jvnet.winp.WinProcess;

import br.com.arbo.opersys.processes.seek.Criteria;
import br.com.arbo.opersys.processes.seek.NotFound;
import br.com.arbo.opersys.processes.seek.UsernameNot;
import br.com.arbo.opersys.processes.seek.windows.FindProcess;
import br.com.arbo.opersys.username.User;

public class KillSteamIfAlreadyRunningInADifferentUserSession {

	private final User username;

	public KillSteamIfAlreadyRunningInADifferentUserSession(
			final User username) {
		this.username = username;
	}

	@SuppressWarnings("static-method")
	public void confirm() {
		final Criteria criteria = new Criteria();
		criteria.usernameNot =
				new UsernameNot(username.username());

		if (!SystemUtils.IS_OS_WINDOWS) return;

		criteria.executable = "steam.exe";

		WinProcess.enableDebugPrivilege();
		final WinProcess steam_exe;
		try {
			steam_exe = FindProcess.seek(criteria);
		} catch (final NotFound e) {
			return;
		}
		steam_exe.killRecursively();
	}

}
