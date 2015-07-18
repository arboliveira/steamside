package br.com.arbo.steamside.api.app;

import java.util.List;
import java.util.stream.Stream;

import br.com.arbo.steamside.collections.Tag;
import br.com.arbo.steamside.collections.TagsQueries;
import br.com.arbo.steamside.steam.client.apps.App;
import br.com.arbo.steamside.steam.client.apps.NotFound;
import br.com.arbo.steamside.steam.client.library.Library;
import br.com.arbo.steamside.steam.client.types.AppId;

public class AppDTO_json
{

	public AppDTO_json(TagsQueries queries, Library library, Limit limit)
	{
		this.limit = limit;
		this.library = library;
		this.queries = queries;
	}

	public List<AppDTO> jsonable(Stream< ? extends Tag> appsOf)
	{
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

	private final Library library;
	private final Limit limit;
	private final TagsQueries queries;

}
