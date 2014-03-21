package br.com.arbo.steamside.favorites;

import java.util.function.Predicate;

import javax.inject.Inject;

import br.com.arbo.steamside.apps.App;
import br.com.arbo.steamside.apps.Filter.Reject;
import br.com.arbo.steamside.apps.FilterPlatform;
import br.com.arbo.steamside.favorites.FavoritesOfUser.NotSet;
import br.com.arbo.steamside.types.Category;

public class Favorites implements Predicate<App> {

	@Inject
	public Favorites(final FavoritesOfUser ofUser) {
		this.ofUser = ofUser;
	}

	@Override
	public boolean test(App app)
	{
		try {
			final Category category = determineCategory();
			if (!app.isInCategory(category)) return false;
			new FilterPlatform().consider(app);
			return true;
		} catch (final Reject e) {
			return false;
		}
	}

	private Category determineCategory()
	{
		try {
			return ofUser.favorites();
		} catch (final NotSet e) {
			return new Category("favorite");
		}
	}

	private final FavoritesOfUser ofUser;

}
