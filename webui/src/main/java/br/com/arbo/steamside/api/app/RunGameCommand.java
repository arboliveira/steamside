package br.com.arbo.steamside.api.app;

import java.util.concurrent.Executors;

import javax.inject.Inject;

import br.com.arbo.java.util.concurrent.DaemonThreadFactory;
import br.com.arbo.steamside.steam.client.apps.home.NotFound;
import br.com.arbo.steamside.steam.client.home.SteamClientHome;
import br.com.arbo.steamside.steam.client.localfiles.appinfo.NotAvailableOnThisPlatform;
import br.com.arbo.steamside.steam.client.platform.PlatformFactory;
import br.com.arbo.steamside.steam.client.protocol.SteamBrowserProtocol;
import br.com.arbo.steamside.steam.client.rungame.RunGame;
import br.com.arbo.steamside.steam.client.rungame.ScheduledExecutorServiceFactory;
import br.com.arbo.steamside.steam.client.rungame.Timeout;
import br.com.arbo.steamside.steam.client.types.AppId;

public class RunGameCommand
{

	public void askSteamToRunGameAndWaitUntilItsUp(AppId appId)
		throws NotAvailableOnThisPlatform, NotFound, Timeout
	{
		runGame.askSteamToRunGameAndWaitUntilItsUp(appId);
	}

	@Inject
	public RunGameCommand(
		SteamBrowserProtocol steam,
		SteamClientHome steamClientHome,
		PlatformFactory platformFactory)
	{
		ScheduledExecutorServiceFactory executorFactory =
			(prefixForThreads -> Executors.newScheduledThreadPool(
				1,
				DaemonThreadFactory.withPrefix(prefixForThreads)));
		this.runGame = new RunGame(
			steam, steamClientHome, executorFactory, platformFactory);
	}

	private final RunGame runGame;

}
