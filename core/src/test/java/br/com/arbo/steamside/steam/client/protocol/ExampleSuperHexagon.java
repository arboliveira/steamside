package br.com.arbo.steamside.steam.client.protocol;

import br.com.arbo.org.apache.commons.lang3.FromSystemUtils;
import br.com.arbo.steamside.opersys.username.FromJava;
import br.com.arbo.steamside.rungame.RunGame;
import br.com.arbo.steamside.steam.client.localfiles.SteamDirectory;
import br.com.arbo.steamside.steam.client.localfiles.appcache.File_appinfo_vdf;
import br.com.arbo.steamside.steam.client.localfiles.appcache.inmemory.InMemory_appinfo_vdf;
import br.com.arbo.steamside.types.AppId;

public class ExampleSuperHexagon {

	public static void main(final String[] args) {
		final AppId appid = new AppId("221640");
		final FromJava user = new FromJava();
		final SteamBrowserProtocol steam = new SteamBrowserProtocol(user);
		final InMemory_appinfo_vdf appinfo = new InMemory_appinfo_vdf(
				new File_appinfo_vdf(new SteamDirectory(new FromSystemUtils())));
		new RunGame(steam, appinfo)
				.askSteamToRunGameAndWaitUntilItsUp(appid);
	}
}
