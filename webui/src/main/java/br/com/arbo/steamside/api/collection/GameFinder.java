package br.com.arbo.steamside.api.collection;

import br.com.arbo.steamside.apps.MissingFrom_appinfo_vdf;
import br.com.arbo.steamside.apps.NotFound;
import br.com.arbo.steamside.library.Library;
import br.com.arbo.steamside.types.AppId;

class GameFinder {

	GameFinder(Library library)
	{
		this.library = library;
	}

	boolean isGame(AppId appid)
	{
		try {
			return this.library.find(appid).type().isGame();
		}
		catch (NotFound notfound) {
			return false;
		}
		catch (MissingFrom_appinfo_vdf missing) {
			return false;
		}
	}

	private final Library library;

}
