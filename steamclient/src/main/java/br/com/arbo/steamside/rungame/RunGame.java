package br.com.arbo.steamside.rungame;

import br.com.arbo.steamside.library.Library;
import br.com.arbo.steamside.steam.client.apps.NotFound;
import br.com.arbo.steamside.steam.client.localfiles.appcache.entry.NotAvailableOnThisPlatform;
import br.com.arbo.steamside.steam.client.protocol.C_rungameid;
import br.com.arbo.steamside.steam.client.protocol.SteamBrowserProtocol;
import br.com.arbo.steamside.steam.client.types.AppId;

public class RunGame {

	private static void askSteamToRunGame(final AppId appid,
			final SteamBrowserProtocol steam)
	{
		steam.launch(new C_rungameid(appid));
	}

	public RunGame(
			final SteamBrowserProtocol steam,
			final Library library,
			ScheduledExecutorServiceFactory executorFactory)
	{
		this.steam = steam;
		this.library = library;
		this.executorFactory = executorFactory;
	}

	public void askSteamToRunGameAndWaitUntilItsUp(final AppId appid)
			throws NotAvailableOnThisPlatform, NotFound, Timeout
	{
		askSteamToRunGame(appid, steam);
		final String exe = findExecutableName(appid);
		new WaitForExecutable(exe, executorFactory).waitFor();
	}

	private String findExecutableName(final AppId appid)
			throws NotAvailableOnThisPlatform, NotFound
	{
		return library.find(appid).executable();
	}

	private final ScheduledExecutorServiceFactory executorFactory;

	private final SteamBrowserProtocol steam;
	private final Library library;
}
