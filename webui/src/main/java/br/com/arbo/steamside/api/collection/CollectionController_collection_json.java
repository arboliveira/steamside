package br.com.arbo.steamside.api.collection;

import java.util.List;
import java.util.stream.Stream;

import br.com.arbo.steamside.api.app.AppApi;
import br.com.arbo.steamside.api.app.AppApiApp;
import br.com.arbo.steamside.api.app.AppDTO;
import br.com.arbo.steamside.api.app.AppDTOListBuilder;
import br.com.arbo.steamside.api.app.Limit;
import br.com.arbo.steamside.collections.Tag;
import br.com.arbo.steamside.collections.TagsQueries;
import br.com.arbo.steamside.collections.system.SystemCollectionsHome;
import br.com.arbo.steamside.steam.client.apps.App;
import br.com.arbo.steamside.steam.client.apps.AppCriteria;
import br.com.arbo.steamside.steam.client.apps.NotFound;
import br.com.arbo.steamside.steam.client.library.Library;
import br.com.arbo.steamside.steam.client.types.AppId;
import br.com.arbo.steamside.types.CollectionName;

class CollectionController_collection_json {

	CollectionController_collection_json(
		String name, Limit limit, SystemCollectionsHome sys,
		Library library,
		TagsQueries queries, boolean gamesOnly)
	{
		this.name = name;
		this.limit = limit;
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
		Stream<AppId> appids = appsOf.map(Tag::appid);

		/*
		Stream<App> apps = new MissingFromLibrary(library)
				.narrow(appids);
				*/

		Stream<AppApi> apps = appids.map(appid -> this.toAppApi(appid));

		AppDTOListBuilder builder = new AppDTOListBuilder();
		builder.cards(apps);
		builder.limit(limit);
		builder.tagsQueries(queries);
		return builder.build();
	}

	private AppApi toAppApi(AppId appid)
	{
		try
		{
			final App app = library.find(appid);
			return new AppApiApp(app);
		}
		catch (NotFound unavailable)
		{
			return new AppApiMissingFromLibrary(appid);
		}
	}

	private final Limit limit;

	private final boolean gamesOnly;

	private final String name;

	private final Library library;

	private final SystemCollectionsHome sys;

	private final TagsQueries queries;

}
