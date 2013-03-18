package br.com.arbo.steamside.steam.client.protocol.examples;

import br.com.arbo.steamside.opersys.username.FromJava;
import br.com.arbo.steamside.rungame.RunGame;
import br.com.arbo.steamside.steam.client.localfiles.appcache.InMemory_appinfo_vdf;
import br.com.arbo.steamside.steam.client.protocol.SteamBrowserProtocol;
import br.com.arbo.steamside.types.AppId;

public class SuperHexagon {

	public static void main(final String[] args) {
		final AppId appid = new AppId("221640");
		final FromJava user = new FromJava();
		final SteamBrowserProtocol steam = new SteamBrowserProtocol(user);
		final InMemory_appinfo_vdf appinfo = new InMemory_appinfo_vdf();
		new RunGame(steam, appinfo, user)
				.askSteamToRunGameAndWaitUntilItsUp(appid);
	}
}
