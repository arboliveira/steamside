package br.com.arbo.steamside.steam.client.executable;

import br.com.arbo.opersys.username.FromJava;

public class ExampleKillSteamNow {

	public static void main(final String[] args)
	{
		new KillSteamIfAlreadyRunningInADifferentUserSession(
				new FromJava())
				.confirm();
	}

}
