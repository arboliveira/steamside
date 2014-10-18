package br.com.arbo.steamside.favorites;

import javax.inject.Inject;

import br.com.arbo.opersys.username.User;
import br.com.arbo.steamside.kids.KidsMode;
import br.com.arbo.steamside.kids.KidsMode.NotInKidsMode;
import br.com.arbo.steamside.types.CollectionName;

public class FromSettings implements FavoritesOfUser {

	@Inject
	public FromSettings(final User user, KidsMode kidsMode)
	{
		this.user = user;
		this.kidsMode = kidsMode;
	}

	@Override
	public CollectionName favorites() throws NotSet
	{
		try
		{
			return fromKid();
		}
		catch (NotInKidsMode e)
		{
			return fromUser();
		}
	}

	private CollectionName fromKid() throws NotInKidsMode
	{
		return kidsMode.kid().getCollection();
	}

	private CollectionName fromUser() throws NotSet
	{
		if (user.username().equals("andre"))
			return new CollectionName("+Favorites");
		throw new NotSet();
	}

	private final KidsMode kidsMode;

	private final User user;

}
