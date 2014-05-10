package br.com.arbo.steamside.favorites;

import java.util.function.Predicate;

import javax.inject.Inject;

import br.com.arbo.steamside.apps.App;
import br.com.arbo.steamside.apps.FilterPlatform;
import br.com.arbo.steamside.favorites.FavoritesOfUser.NotSet;
import br.com.arbo.steamside.settings.Settings;
import br.com.arbo.steamside.types.Category;

public class Favorites implements Predicate<App> {

	@Inject
	public Favorites(final FavoritesOfUser ofUser, Settings settings) {
		this.ofUser = ofUser;
		currentPlatformOnly = settings.currentPlatformOnly();
	}

	@Override
	public boolean test(App app)
	{
		final Category category = determineCategory();
		if (!app.isInCategory(category)) return false;
		if (currentPlatformOnly)
			return new FilterPlatform().test(app);
		return true;
	}

	private Category determineCategory()
	{
		try {
			return ofUser.favorites();
		}
		catch (final NotSet e) {
			return new Category("favorite");
		}
	}

	private final boolean currentPlatformOnly;

	private final FavoritesOfUser ofUser;

}
