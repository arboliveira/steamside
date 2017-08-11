package br.com.arbo.steamside.kids;

import java.util.Optional;

import javax.inject.Inject;

import br.com.arbo.opersys.username.User;

public class FromUsername implements KidsMode
{

	@Override
	public Optional<Kid> kid() throws NotInKidsMode
	{
		try
		{
			return Optional.of(kids.find(user));
		}
		catch (NotFound e)
		{
			throw new NotInKidsMode(e);
		}
	}

	@Inject
	public FromUsername(final User user, final Kids kids)
	{
		this.user = user;
		this.kids = kids;
	}

	private final Kids kids;

	private final User user;

}
