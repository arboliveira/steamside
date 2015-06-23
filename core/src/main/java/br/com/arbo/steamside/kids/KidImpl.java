package br.com.arbo.steamside.kids;

import br.com.arbo.opersys.username.User;
import br.com.arbo.steamside.types.CollectionName;

public class KidImpl implements Kid
{

	public static KidImpl clone(Kid kid)
	{
		return new KidImpl(kid.getName(), kid.getUser(), kid.getCollection());
	}

	public KidImpl(KidName name, User user, CollectionName collection)
	{
		this.name = name;
		this.user = user;
		this.collection = collection;
	}

	@Override
	public CollectionName getCollection()
	{
		return collection;
	}

	@Override
	public KidName getName()
	{
		return name;
	}

	@Override
	public User getUser()
	{
		return user;
	}

	private final CollectionName collection;

	private final KidName name;

	private final User user;

}
