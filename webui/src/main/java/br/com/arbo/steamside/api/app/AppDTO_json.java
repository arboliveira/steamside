package br.com.arbo.steamside.api.app;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import br.com.arbo.steamside.collections.Tag;
import br.com.arbo.steamside.collections.TagsQueries;
import br.com.arbo.steamside.steam.client.apps.App;
import br.com.arbo.steamside.steam.client.apps.home.AppCriteria;
import br.com.arbo.steamside.steam.client.home.SteamClientHome;
import br.com.arbo.steamside.steam.client.platform.PlatformCheck;
import br.com.arbo.steamside.steam.client.platform.PlatformFactory;
import br.com.arbo.steamside.steam.client.types.AppId;
import br.com.arbo.steamside.steam.client.types.AppName;

public class AppDTO_json
{

	public List<AppCardDTO> jsonable(Stream< ? extends Tag> tags)
	{
		return steamClientHome.apps()
			.find(
				tags.map(Tag::appid),
				new AppCriteria().lastPlayedDescending(true))
			.map(app -> this.valueOf(app.appid())).limit(limit.limit())
			.collect(Collectors.toList());
	}

	public AppDTO_json(TagsQueries tagsQueries, SteamClientHome steamClientHome,
		PlatformFactory platformFactory,
		Limit limit)
	{
		this.steamClientHome = steamClientHome;
		this.platformFactory = platformFactory;
		this.limit = limit;
		this.tagsQueries = tagsQueries;
	}

	private AppCardDTO valueOf(AppId appid)
	{
		Optional<App> find = steamClientHome.apps().find(appid);

		if (find.isPresent())
		{
			App app = find.get();

			return new AppCardDTOBuilder()
				.appid(app.appid())
				.name(app.name())
				.tags(AppDTOFactory.tags_jsonable(appid, tagsQueries))
				.unavailable(
					!new PlatformCheck().app(app)
						.platform(platformFactory.current())
						.isAvailable())
				.build();
		}

		return new AppCardDTOBuilder()
			.appid(appid)
			.name(new AppName(appid + " [missing from library]"))
			.tags(AppDTOFactory.tags_jsonable(appid, tagsQueries))
			.unavailable(false)
			.build();
	}

	private final Limit limit;

	private final PlatformFactory platformFactory;

	private final SteamClientHome steamClientHome;
	private final TagsQueries tagsQueries;

}
