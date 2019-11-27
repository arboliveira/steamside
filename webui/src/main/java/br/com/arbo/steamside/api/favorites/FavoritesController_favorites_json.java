package br.com.arbo.steamside.api.favorites;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.inject.Inject;

import br.com.arbo.steamside.api.app.AppCardDTO;
import br.com.arbo.steamside.api.app.AppCardDTOBuilder;
import br.com.arbo.steamside.api.app.AppDTOFactory;
import br.com.arbo.steamside.api.app.AppSettings;
import br.com.arbo.steamside.api.app.Limit;
import br.com.arbo.steamside.collections.Tag;
import br.com.arbo.steamside.collections.TagsQueries;
import br.com.arbo.steamside.favorites.FavoritesOfUser;
import br.com.arbo.steamside.settings.Settings;
import br.com.arbo.steamside.steam.client.apps.App;
import br.com.arbo.steamside.steam.client.apps.home.AppCriteria;
import br.com.arbo.steamside.steam.client.home.SteamClientHome;
import br.com.arbo.steamside.steam.client.internal.platform.PlatformFactoryImpl;
import br.com.arbo.steamside.steam.client.platform.PlatformCheck;
import br.com.arbo.steamside.steam.client.platform.PlatformFactory;
import br.com.arbo.steamside.types.CollectionName;

public class FavoritesController_favorites_json
	implements FavoritesController_favorites
{

	@Override
	public List<AppCardDTO> jsonable()
	{
		return steamClientHome.apps()
			.find(
				tagsQueries
					.appsOf(determineCollection())
					.map(Tag::appid),
				new AppCriteria()
					.gamesOnly(settings.gamesOnly())
					.lastPlayedDescending(true)
					.platform(
						settings.currentPlatformOnly()
							? Optional.of(new PlatformFactoryImpl().current())
							: Optional.empty()))
			.map(this::toOptionalDTO)
			.filter(Optional::isPresent)
			.map(Optional::get)
			.limit(limit.limit())
			.collect(Collectors.toList());
	}

	@Inject
	public FavoritesController_favorites_json(
		FavoritesOfUser ofUser,
		SteamClientHome steamClientHome, Settings settings,
		PlatformFactory platformFactory,
		AppSettings apiAppSettings,
		TagsQueries queries)
	{
		this.ofUser = ofUser;
		this.steamClientHome = steamClientHome;
		this.settings = settings;
		this.platformFactory = platformFactory;
		this.limit = apiAppSettings.limit();
		this.tagsQueries = queries;
	}

	final Settings settings;

	final TagsQueries tagsQueries;

	private CollectionName determineCollection()
	{
		return ofUser.favorites().orElse(new CollectionName("favorite"));
	}

	private boolean isAvailable(App app)
	{
		return new PlatformCheck().app(app)
			.platform(platformFactory.current())
			.isAvailable();
	}

	private Optional<AppCardDTO> toOptionalDTO(App app)
	{
		return Optional.of(valueOf(app));
	}

	private AppCardDTO valueOf(App app)
	{
		return new AppCardDTOBuilder()
			.appid(app.appid())
			.name(app.name())
			.tags(AppDTOFactory.tags_jsonable(app.appid(), tagsQueries))
			.unavailable(
				!isAvailable(app))
			.build();
	}

	private final Limit limit;

	private final FavoritesOfUser ofUser;
	private final PlatformFactory platformFactory;
	private final SteamClientHome steamClientHome;

}
