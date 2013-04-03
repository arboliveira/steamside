package br.com.arbo.steamside.favorites;

import br.com.arbo.steamside.opersys.username.User;
import br.com.arbo.steamside.types.Category;

public class FromSettings implements FavoritesOfUser {

	private final User user;

	public FromSettings(final User user) {
		this.user = user;
	}

	@Override
	public Category favorites() throws NotSet {
		if (user.username().equals("andre"))
			return new Category("+Favorites");
		throw new NotSet();
	}
}
