package br.com.arbo.steamside.kids;

import javax.inject.Inject;

import br.com.arbo.opersys.username.User;
import br.com.arbo.steamside.types.CollectionName;

public class FromUsername implements KidsMode {

	@Inject
	public FromUsername(final User user, final Kids kids) {
		this.user = user;
		this.kids = kids;
	}

	@Override
	public CollectionName getCollection()
	{
		return kids.find(user).getCollection();
	}

	@Override
	public boolean isKidsModeOn()
	{
		try {
			kids.find(user);
			return true;
		}
		catch (NotFound e) {
			return false;
		}
	}

	private final Kids kids;

	private final User user;

}
