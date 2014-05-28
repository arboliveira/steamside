package br.com.arbo.steamside.library;

import br.com.arbo.steamside.steam.client.apps.NotFound;
import br.com.arbo.steamside.steam.client.types.AppId;

public class GameFinder {

	public GameFinder(Library library)
	{
		this.library = library;
	}

	public boolean isGame(AppId appid)
	{
		try {
			return this.library.find(appid).isGame();
		}
		catch (NotFound notfound) {
			return false;
		}
	}

	private final Library library;

}
