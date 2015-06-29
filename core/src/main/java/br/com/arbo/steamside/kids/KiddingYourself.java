package br.com.arbo.steamside.kids;

import br.com.arbo.opersys.username.User;

public class KiddingYourself extends RuntimeException
{

	public static void requireDifferentUser(Kid kid, User user)
		throws KiddingYourself
	{
		if (kid.getUser().equalsUsername(user))
			throw new KiddingYourself();
	}

	KiddingYourself()
	{
		super(
			"Kidding yourself?"
				+ " If you add a kid with your own OS user name"
				+ " you will get locked out.");
	}

}
