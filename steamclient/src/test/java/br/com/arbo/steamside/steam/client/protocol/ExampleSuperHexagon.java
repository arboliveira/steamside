package br.com.arbo.steamside.steam.client.protocol;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;

import br.com.arbo.opersys.username.FromJava;
import br.com.arbo.steamside.steam.client.library.Libraries;
import br.com.arbo.steamside.steam.client.library.Library;
import br.com.arbo.steamside.steam.client.rungame.RunGame;
import br.com.arbo.steamside.steam.client.types.AppId;

public class ExampleSuperHexagon {

	public static void main(final String[] args)
			throws Exception
	{
		final AppId appid = new AppId("221640");
		final FromJava user = new FromJava();
		final SteamBrowserProtocol steam = new SteamBrowserProtocol(user);

		final Library library = Libraries.fromSteamPhysicalFiles();

		ScheduledExecutorService executorService =
				Executors.newSingleThreadScheduledExecutor();
		try {
			new RunGame(steam, library, (prefix -> executorService))
			.askSteamToRunGameAndWaitUntilItsUp(appid);
		}
		finally {
			executorService.shutdown();
		}

	}
}
