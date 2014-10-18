package br.com.arbo.steamside.kids;

import javax.inject.Inject;

import br.com.arbo.opersys.username.User;

public class FromUsername implements KidsMode {

	@Inject
	public FromUsername(final User user, final Kids kids)
	{
		this.user = user;
		this.kids = kids;
	}

	@Override
	public Kid kid() throws NotInKidsMode
	{
		try
		{
			return kids.find(user);
		}
		catch (NotFound e)
		{
			throw new NotInKidsMode(e);
		}
	}

	private final Kids kids;

	private final User user;

}
