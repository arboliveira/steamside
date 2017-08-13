package br.com.arbo.steamside.api.favorites;

import java.util.List;
import java.util.stream.Stream;

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
import br.com.arbo.steamside.steam.client.apps.App;
import br.com.arbo.steamside.steam.client.apps.AppCriteria;
import br.com.arbo.steamside.steam.client.library.Library;
import br.com.arbo.steamside.steam.client.library.MissingFromLibrary;
import br.com.arbo.steamside.types.CollectionName;

public class FavoritesController_favorites_json
	implements FavoritesController_favorites
{

	@Override
	public List<AppDTO> jsonable()
	{
		final CollectionName collection = determineCollection();

		final Stream< ? extends Tag> appsOf = queries.appsOf(collection);

		Stream<App> apps =
			new MissingFromLibrary(library)
				.narrow(appsOf.map(Tag::appid));

		Stream<App> filtered = new AppCriteria()
		{

			{
				gamesOnly = settings.gamesOnly();
				currentPlatformOnly = settings.currentPlatformOnly();
			}
		}.filter(apps);

		AppDTOListBuilder builder = new AppDTOListBuilder();
		builder.cards(filtered.map(AppApiApp::new));
		builder.limit(limit);
		builder.tagsQueries(queries);
		return builder.build();
	}

	private CollectionName determineCollection()
	{
		return ofUser.favorites().orElse(new CollectionName("favorite"));
	}

	@Inject
	public FavoritesController_favorites_json(
		FavoritesOfUser ofUser,
		Library library, Settings settings,
		AppSettings apiAppSettings,
		TagsQueries queries)
	{
		this.ofUser = ofUser;
		this.library = library;
		this.settings = settings;
		this.limit = apiAppSettings.limit();
		this.queries = queries;
	}

	final Settings settings;

	private final TagsQueries queries;

	private final Limit limit;

	private final FavoritesOfUser ofUser;

	private final Library library;

}
