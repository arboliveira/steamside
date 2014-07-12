package br.com.arbo.steamside.favorites;

import javax.inject.Inject;

import br.com.arbo.opersys.username.User;
import br.com.arbo.steamside.types.CollectionName;

public class FromSettings implements FavoritesOfUser {

	@Inject
	public FromSettings(final User user)
	{
		this.user = user;
	}

	@Override
	public CollectionName favorites() throws NotSet
	{
		if (user.username().equals("andre"))
			return new CollectionName("+Favorites");
		throw new NotSet();
	}

	private final User user;

}
