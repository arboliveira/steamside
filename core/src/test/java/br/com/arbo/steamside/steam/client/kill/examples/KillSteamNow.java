package br.com.arbo.steamside.steam.client.kill.examples;

import br.com.arbo.steamside.opersys.username.UsernameFromJava;
import br.com.arbo.steamside.steam.client.executable.KillSteamIfAlreadyRunningInADifferentUserSession;

public class KillSteamNow {

	public static void main(final String[] args) {
		new KillSteamIfAlreadyRunningInADifferentUserSession(
				new UsernameFromJava())
				.confirm();
	}

}
