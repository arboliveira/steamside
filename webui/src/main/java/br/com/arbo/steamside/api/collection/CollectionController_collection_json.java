package br.com.arbo.steamside.api.collection;

import java.util.List;
import java.util.stream.Stream;

import br.com.arbo.steamside.api.app.AppDTO;
import br.com.arbo.steamside.api.app.AppsDTO;
import br.com.arbo.steamside.apps.AppCriteria;
import br.com.arbo.steamside.collections.CollectionsQueries;
import br.com.arbo.steamside.collections.Tag;
import br.com.arbo.steamside.collections.system.SystemCollectionsHome;
import br.com.arbo.steamside.library.Library;
import br.com.arbo.steamside.steam.client.types.AppId;
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
	}

	List<AppDTO> jsonable()
	{
		boolean _gamesOnly = gamesOnly;
		final Stream< ? extends Tag> appsOf =
				sys.appsOf(new CollectionName(name), new AppCriteria() {

					{
						this.gamesOnly = _gamesOnly;
					}
				});
		final Stream<AppId> appids = appsOf.map(Tag::appid);
		return new AppsDTO(appids, library, queries).jsonable();
	}

	private final boolean gamesOnly;

	private final String name;

	private final Library library;

	private final SystemCollectionsHome sys;

	private final CollectionsQueries queries;

}
