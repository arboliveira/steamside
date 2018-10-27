package br.com.arbo.steamside.steam.client.rungame;

import java.util.Optional;

import br.com.arbo.steamside.steam.client.apps.App;
import br.com.arbo.steamside.steam.client.apps.home.NotFound;
import br.com.arbo.steamside.steam.client.home.SteamClientHome;
import br.com.arbo.steamside.steam.client.localfiles.appinfo.NotAvailableOnThisPlatform;
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
		ScheduledExecutorServiceFactory executorFactory)
	{
		this.steam = steam;
		this.steamClientHome = steamClientHome;
		this.executorFactory = executorFactory;
	}

	private Optional<String> findExecutableName(final AppId appid)
		throws NotAvailableOnThisPlatform
	{
		return steamClientHome.apps().find(appid).map(App::executable);
	}

	private static void askSteamToRunGame(final AppId appid,
		final SteamBrowserProtocol steam)
	{
		steam.launch(new C_rungameid(appid));
	}

	private final ScheduledExecutorServiceFactory executorFactory;

	private final SteamBrowserProtocol steam;
	private final SteamClientHome steamClientHome;
}
