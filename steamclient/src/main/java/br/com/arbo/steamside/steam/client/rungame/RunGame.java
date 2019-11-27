package br.com.arbo.steamside.steam.client.rungame;

import java.util.Optional;

import br.com.arbo.steamside.steam.client.apps.App;
import br.com.arbo.steamside.steam.client.apps.home.NotFound;
import br.com.arbo.steamside.steam.client.home.SteamClientHome;
import br.com.arbo.steamside.steam.client.localfiles.appinfo.NotAvailableOnThisPlatform;
import br.com.arbo.steamside.steam.client.platform.PlatformFactory;
import br.com.arbo.steamside.steam.client.protocol.C_rungameid;
import br.com.arbo.steamside.steam.client.protocol.SteamBrowserProtocol;
import br.com.arbo.steamside.steam.client.types.AppId;

public class RunGame
{

	public void askSteamToRunGameAndWaitUntilItsUp(final AppId appid)
		throws NotAvailableOnThisPlatform, NotFound, Timeout
	{
		askSteamToRunGame(appid, steam);
		final Optional<String> exe = findExecutableName(appid);
		new WaitForExecutable(
			exe.orElseThrow(() -> NotFound.appid(appid.appid())),
			executorFactory)
				.waitFor();
	}

	public RunGame(
		SteamBrowserProtocol steam,
		SteamClientHome steamClientHome,
		ScheduledExecutorServiceFactory executorFactory,
		PlatformFactory platformFactory)
	{
		this.steam = steam;
		this.steamClientHome = steamClientHome;
		this.executorFactory = executorFactory;
		this.platformFactory = platformFactory;
	}

	private Optional<String> executable(App app)
	{
		return app.executable(platformFactory.current());
	}

	private Optional<String> findExecutableName(AppId appid)
		throws NotAvailableOnThisPlatform
	{
		return steamClientHome.apps()
			.find(appid)
			.map(this::executable)
			.orElseThrow(NotAvailableOnThisPlatform::new);
	}

	private static void askSteamToRunGame(final AppId appid,
		final SteamBrowserProtocol steam)
	{
		steam.launch(new C_rungameid(appid));
	}

	private final ScheduledExecutorServiceFactory executorFactory;
	private final PlatformFactory platformFactory;
	private final SteamBrowserProtocol steam;
	private final SteamClientHome steamClientHome;
}
