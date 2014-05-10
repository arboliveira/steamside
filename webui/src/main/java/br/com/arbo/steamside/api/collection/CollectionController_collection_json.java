package br.com.arbo.steamside.api.collection;

import java.util.List;
import java.util.stream.Stream;

import br.com.arbo.steamside.api.app.AppDTO;
import br.com.arbo.steamside.api.app.AppsDTO;
import br.com.arbo.steamside.collections.CollectionsQueries;
import br.com.arbo.steamside.collections.Tag;
import br.com.arbo.steamside.collections.system.SystemCollectionsHome;
import br.com.arbo.steamside.library.Library;
import br.com.arbo.steamside.types.AppId;
import br.com.arbo.steamside.types.CollectionName;

class CollectionController_collection_json {

	CollectionController_collection_json(
			String name, SystemCollectionsHome sys, Library library,
			CollectionsQueries queries, boolean gamesOnly)
	{
		this.name = name;
		this.sys = sys;
		this.library = library;
		this.queries = queries;
		this.gamesOnly = gamesOnly;
		this.gameFinder = new GameFinder(library);
	}

	List<AppDTO> jsonable()
	{
		final Stream<AppId> apps =
				sys.appsOf(new CollectionName(name)).map(
						Tag::appid);
		final Stream<AppId> end =
				gamesOnly ?
						apps.filter(this::isGame)
						: apps;
		return new AppsDTO(end, library, queries).jsonable();
	}

	private boolean isGame(AppId appid)
	{
		return gameFinder.isGame(appid);
	}

	private final boolean gamesOnly;

	private final String name;

	private final Library library;

	private final SystemCollectionsHome sys;

	private final CollectionsQueries queries;

	private final GameFinder gameFinder;

}
