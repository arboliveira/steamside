package br.com.arbo.steamside.api.favorites;

import java.util.List;
import java.util.stream.Stream;

import br.com.arbo.steamside.api.app.AppApiApp;
import br.com.arbo.steamside.api.app.AppDTO;
import br.com.arbo.steamside.api.app.AppsDTO;
import br.com.arbo.steamside.api.app.Limit;
import br.com.arbo.steamside.collections.Tag;
import br.com.arbo.steamside.collections.TagsQueries;
import br.com.arbo.steamside.favorites.FavoritesOfUser;
import br.com.arbo.steamside.favorites.FavoritesOfUser.NotSet;
import br.com.arbo.steamside.settings.Settings;
import br.com.arbo.steamside.steam.client.apps.App;
import br.com.arbo.steamside.steam.client.apps.AppCriteria;
import br.com.arbo.steamside.steam.client.library.Library;
import br.com.arbo.steamside.steam.client.library.MissingFromLibrary;
import br.com.arbo.steamside.types.CollectionName;

class FavoritesController_favorites_json {

	FavoritesController_favorites_json(
			FavoritesOfUser ofUser,
			Library library, Settings settings, Limit limit,
			TagsQueries queries)
	{
		this.ofUser = ofUser;
		this.library = library;
		this.settings = settings;
		this.limit = limit;
		this.queries = queries;
	}

	List<AppDTO> jsonable()
	{
		final CollectionName collection = determineCollection();

		final Stream< ? extends Tag> appsOf = queries.appsOf(collection);

		Stream<App> apps =
				new MissingFromLibrary(library)
						.narrow(appsOf.map(Tag::appid));

		Stream<App> filtered = new AppCriteria() {

			{
				gamesOnly = settings.gamesOnly();
				currentPlatformOnly = settings.currentPlatformOnly();
			}
		}.filter(apps);

		return new AppsDTO(
				filtered.map(AppApiApp::new),
				limit, queries).jsonable();
	}

	private CollectionName determineCollection()
	{
		try
		{
			return ofUser.favorites();
		}
		catch (final NotSet e)
		{
			return new CollectionName("favorite");
		}
	}

	final Settings settings;

	private final TagsQueries queries;

	private final Limit limit;

	private final FavoritesOfUser ofUser;

	Library library;

}
