package br.com.arbo.steamside.steam.client.protocol;

import br.com.arbo.steamside.library.Library;
import br.com.arbo.steamside.library.Library_ForExamples;
import br.com.arbo.steamside.opersys.username.FromJava;
import br.com.arbo.steamside.rungame.RunGame;
import br.com.arbo.steamside.types.AppId;

public class ExampleSuperHexagon {

	public static void main(final String[] args) {
		final AppId appid = new AppId("221640");
		final FromJava user = new FromJava();
		final SteamBrowserProtocol steam = new SteamBrowserProtocol(user);
		final Library library = Library_ForExamples.fromSteamPhysicalFiles();
		new RunGame(steam, library)
				.askSteamToRunGameAndWaitUntilItsUp(appid);
	}
}
