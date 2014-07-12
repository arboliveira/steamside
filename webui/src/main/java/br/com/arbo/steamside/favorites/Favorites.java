package br.com.arbo.steamside.favorites;

import java.util.function.Predicate;

import javax.inject.Inject;

import br.com.arbo.steamside.apps.FilterPlatform;
import br.com.arbo.steamside.collections.TagsQueries;
import br.com.arbo.steamside.favorites.FavoritesOfUser.NotSet;
import br.com.arbo.steamside.settings.Settings;
import br.com.arbo.steamside.steam.client.apps.App;
import br.com.arbo.steamside.types.CollectionName;

public class Favorites implements Predicate<App> {

	@Inject
	public Favorites(
			final FavoritesOfUser ofUser, Settings settings,
			TagsQueries queries)
	{
		this.ofUser = ofUser;
		this.queries = queries;
		currentPlatformOnly = settings.currentPlatformOnly();
	}

	@Override
	public boolean test(App app)
	{
		final CollectionName collection = determineCollection();
		if (!queries.isTagged(app.appid(), collection)) return false;
		if (currentPlatformOnly)
			return new FilterPlatform().test(app);
		return true;
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

	private final boolean currentPlatformOnly;

	private final FavoritesOfUser ofUser;

	private final TagsQueries queries;

}
