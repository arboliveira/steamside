package br.com.arbo.steamside.favorites;

import br.com.arbo.steamside.apps.App;
import br.com.arbo.steamside.collection.Filter;
import br.com.arbo.steamside.collection.FilterPlatform;
import br.com.arbo.steamside.favorites.FavoritesOfUser.NotSet;
import br.com.arbo.steamside.steam.client.localfiles.appcache.InMemory_appinfo_vdf;
import br.com.arbo.steamside.types.Category;

public class Favorites implements Filter {

	private final InMemory_appinfo_vdf appinfo;
	private final FavoritesOfUser ofUser;

	public Favorites(final FavoritesOfUser ofUser,
			final InMemory_appinfo_vdf appinfo) {
		this.ofUser = ofUser;
		this.appinfo = appinfo;
	}

	private Category determineCategory() {
		try {
			return ofUser.favorites();
		} catch (final NotSet e) {
			return new Category("favorite");
		}
	}

	@Override
	public void consider(final App app) throws Reject {
		final Category category = determineCategory();
		if (!app.isInCategory(category)) throw new Reject();
		new FilterPlatform(appinfo).consider(app);
	}

}
