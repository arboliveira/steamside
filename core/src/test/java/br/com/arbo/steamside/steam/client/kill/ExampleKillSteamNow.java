package br.com.arbo.steamside.steam.client.kill;

import br.com.arbo.steamside.opersys.username.FromJava;
import br.com.arbo.steamside.steam.client.executable.KillSteamIfAlreadyRunningInADifferentUserSession;

public class ExampleKillSteamNow {

	public static void main(final String[] args) {
		new KillSteamIfAlreadyRunningInADifferentUserSession(
				new FromJava())
				.confirm();
	}

}
