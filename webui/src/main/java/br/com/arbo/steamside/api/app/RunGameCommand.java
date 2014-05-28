package br.com.arbo.steamside.api.app;

import java.util.concurrent.Executors;

import javax.inject.Inject;

import br.com.arbo.java.util.concurrent.DaemonThreadFactory;
import br.com.arbo.steamside.rungame.RunGame;
import br.com.arbo.steamside.rungame.ScheduledExecutorServiceFactory;
import br.com.arbo.steamside.steam.client.library.Library;
import br.com.arbo.steamside.steam.client.protocol.SteamBrowserProtocol;
import br.com.arbo.steamside.steam.client.types.AppId;

public class RunGameCommand {

	@Inject
	public RunGameCommand(
			final SteamBrowserProtocol steam,
			final Library library)
	{
		ScheduledExecutorServiceFactory executorFactory =
				(prefixForThreads -> Executors.newScheduledThreadPool(
						1,
						DaemonThreadFactory.withPrefix(prefixForThreads)));
		this.runGame = new RunGame(steam, library, executorFactory);
	}

	public void askSteamToRunGameAndWaitUntilItsUp(AppId appId)
	{
		runGame.askSteamToRunGameAndWaitUntilItsUp(appId);
	}

	private final RunGame runGame;

}
