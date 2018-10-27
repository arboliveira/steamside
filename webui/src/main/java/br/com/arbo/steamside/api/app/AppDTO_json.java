package br.com.arbo.steamside.api.app;

import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

import br.com.arbo.steamside.collections.Tag;
import br.com.arbo.steamside.collections.TagsQueries;
import br.com.arbo.steamside.steam.client.home.SteamClientHome;
import br.com.arbo.steamside.steam.client.types.AppId;

public class AppDTO_json
{

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

	public AppDTO_json(TagsQueries queries, SteamClientHome steamClientHome,
		Limit limit)
	{
		this.steamClientHome = steamClientHome;
		this.limit = limit;
		this.queries = queries;
	}

	private AppApi toAppApi(AppId appid)
	{
		Optional<AppApi> map =
			steamClientHome.apps().find(appid).map(AppApiApp::new);

		return map.orElseGet(() -> new AppApiMissingFromLibrary(appid));
	}

	private final Limit limit;

	private final TagsQueries queries;
	private final SteamClientHome steamClientHome;

}
