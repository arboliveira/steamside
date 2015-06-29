package br.com.arbo.steamside.kids;

import br.com.arbo.opersys.username.User;

public class KidCheckImpl implements KidCheck
{

	public KidCheckImpl(Kid kid, User user)
	{
		this.kid = kid;
		this.user = user;
	}

	@Override
	public Kid check() throws KiddingYourself
	{
		KiddingYourself.requireDifferentUser(kid, user);
		return kid;
	}

	private final Kid kid;

	private final User user;

}
