package br.com.arbo.steamside.favorites;

import javax.inject.Inject;

import br.com.arbo.steamside.apps.App;
import br.com.arbo.steamside.apps.Filter;
import br.com.arbo.steamside.apps.FilterPlatform;
import br.com.arbo.steamside.favorites.FavoritesOfUser.NotSet;
import br.com.arbo.steamside.types.Category;

public class Favorites implements Filter {

	@Override
	public void consider(final App app) throws Reject {
		final Category category = determineCategory();
		if (!app.isInCategory(category)) throw new Reject();
		new FilterPlatform().consider(app);
	}

	private Category determineCategory() {
		try {
			return ofUser.favorites();
		} catch (final NotSet e) {
			return new Category("favorite");
		}
	}

	private final FavoritesOfUser ofUser;

	@Inject
	public Favorites(final FavoritesOfUser ofUser) {
		this.ofUser = ofUser;
	}

}
