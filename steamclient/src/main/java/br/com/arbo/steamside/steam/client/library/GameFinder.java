package br.com.arbo.steamside.steam.client.library;

import br.com.arbo.steamside.steam.client.apps.App;
import br.com.arbo.steamside.steam.client.home.SteamClientHome;
import br.com.arbo.steamside.steam.client.types.AppId;

public class GameFinder
{

	public boolean isGame(AppId appid)
	{
		return this.steamClientHome.apps().find(appid).map(App::isGame)
			.orElse(true);
	}

	public GameFinder(SteamClientHome steamClientHome)
	{
		this.steamClientHome = steamClientHome;
	}

	private final SteamClientHome steamClientHome;

}
