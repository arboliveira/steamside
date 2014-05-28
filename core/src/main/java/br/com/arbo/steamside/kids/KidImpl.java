package br.com.arbo.steamside.kids;

import br.com.arbo.opersys.username.User;
import br.com.arbo.steamside.types.CollectionName;

public class KidImpl implements Kid {

	public static KidImpl clone(Kid kid)
	{
		return new KidImpl(kid.getUser(), kid.getCollection());
	}

	public KidImpl(User user, CollectionName collection) {
		this.user = user;
		this.collection = collection;
	}

	@Override
	public CollectionName getCollection()
	{
		return collection;
	}

	@Override
	public User getUser()
	{
		return user;
	}

	private final CollectionName collection;

	private final User user;

}
