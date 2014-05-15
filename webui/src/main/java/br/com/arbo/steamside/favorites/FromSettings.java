package br.com.arbo.steamside.favorites;

import javax.inject.Inject;

import br.com.arbo.steamside.opersys.username.User;
import br.com.arbo.steamside.types.SteamCategory;

public class FromSettings implements FavoritesOfUser {

	@Override
	public SteamCategory favorites() throws NotSet {
		if (user.username().equals("andre"))
			return new SteamCategory("+Favorites");
		throw new NotSet();
	}

	private final User user;

	@Inject
	public FromSettings(final User user) {
		this.user = user;
	}

}
