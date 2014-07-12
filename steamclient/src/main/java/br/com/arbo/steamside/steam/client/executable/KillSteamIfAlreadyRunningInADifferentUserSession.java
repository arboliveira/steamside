package br.com.arbo.steamside.steam.client.executable;

import br.com.arbo.opersys.processes.seek.Criteria;
import br.com.arbo.opersys.processes.seek.UsernameNot;
import br.com.arbo.opersys.username.User;

public class KillSteamIfAlreadyRunningInADifferentUserSession {

	public KillSteamIfAlreadyRunningInADifferentUserSession(
			final User username)
	{
		this.username = username;
	}

	@SuppressWarnings("static-method")
	public void confirm()
	{
		final Criteria criteria = new Criteria();
		criteria.usernameNot =
				new UsernameNot(username.username());

		SteamExeFindProcess.seek(criteria, exe -> exe.killRecursively());
	}

	private final User username;

}
