package br.com.arbo.steamside.api.favorites;

import java.util.List;

import javax.inject.Inject;

import br.com.arbo.steamside.api.app.AppApiApp;
import br.com.arbo.steamside.api.app.AppDTO;
import br.com.arbo.steamside.api.app.AppDTOListBuilder;
import br.com.arbo.steamside.api.app.AppSettings;
import br.com.arbo.steamside.api.app.Limit;
import br.com.arbo.steamside.collections.Tag;
import br.com.arbo.steamside.collections.TagsQueries;
import br.com.arbo.steamside.favorites.FavoritesOfUser;
import br.com.arbo.steamside.settings.Settings;
import br.com.arbo.steamside.steam.client.apps.home.AppCriteria;
import br.com.arbo.steamside.steam.client.home.SteamClientHome;
import br.com.arbo.steamside.types.CollectionName;

public class FavoritesController_favorites_json
	implements FavoritesController_favorites
{

	@Override
	public List<AppDTO> jsonable()
	{
		final CollectionName collection = determineCollection();

		return new AppDTOListBuilder()
			.cards(
				steamClientHome.apps().stream(
					new AppCriteria()
						.in(queries.appsOf(collection).map(Tag::appid))
						.gamesOnly(settings.gamesOnly())
						.currentPlatformOnly(settings.currentPlatformOnly()))
					.map(AppApiApp::new))
			.limit(limit)
			.tagsQueries(queries)
			.build();
	}

	@Inject
	public FavoritesController_favorites_json(
		FavoritesOfUser ofUser,
		SteamClientHome steamClientHome, Settings settings,
		AppSettings apiAppSettings,
		TagsQueries queries)
	{
		this.ofUser = ofUser;
		this.steamClientHome = steamClientHome;
		this.settings = settings;
		this.limit = apiAppSettings.limit();
		this.queries = queries;
	}

	final TagsQueries queries;
	final Settings settings;

	private CollectionName determineCollection()
	{
		return ofUser.favorites().orElse(new CollectionName("favorite"));
	}

	private final Limit limit;
	private final FavoritesOfUser ofUser;
	private final SteamClientHome steamClientHome;

}
