package br.com.arbo.steamside.favorites;

import javax.inject.Inject;

import br.com.arbo.steamside.opersys.username.User;
import br.com.arbo.steamside.types.Category;

public class FromSettings implements FavoritesOfUser {

	@Override
	public Category favorites() throws NotSet {
		if (user.username().equals("andre"))
			return new Category("+Favorites");
		throw new NotSet();
	}

	private final User user;

	@Inject
	public FromSettings(final User user) {
		this.user = user;
	}

}
