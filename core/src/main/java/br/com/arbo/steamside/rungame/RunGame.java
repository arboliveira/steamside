package br.com.arbo.steamside.rungame;

import javax.inject.Inject;

import br.com.arbo.steamside.apps.NotFound;
import br.com.arbo.steamside.library.Library;
import br.com.arbo.steamside.steam.client.localfiles.appcache.entry.NotAvailableOnThisPlatform;
import br.com.arbo.steamside.steam.client.protocol.C_rungameid;
import br.com.arbo.steamside.steam.client.protocol.SteamBrowserProtocol;
import br.com.arbo.steamside.types.AppId;

public class RunGame {

	@Inject
	public RunGame(
			final SteamBrowserProtocol steam,
			final Library library) {
		this.steam = steam;
		this.library = library;
	}

	public void askSteamToRunGameAndWaitUntilItsUp(final AppId appid)
			throws NotAvailableOnThisPlatform, NotFound, Timeout {
		askSteamToRunGame(appid, steam);
		final String exe = findExecutableName(appid);
		new WaitForExecutable(exe).waitFor();
	}

	private static void askSteamToRunGame(final AppId appid,
			final SteamBrowserProtocol steam) {
		steam.launch(new C_rungameid(appid));
	}

	private String findExecutableName(final AppId appid)
			throws NotAvailableOnThisPlatform, NotFound
	{
		return library.find(appid).executable();
	}

	private final SteamBrowserProtocol steam;
	private final Library library;

}
