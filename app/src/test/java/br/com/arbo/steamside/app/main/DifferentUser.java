package br.com.arbo.steamside.app.main;

import org.mockito.Mockito;

import br.com.arbo.opersys.username.User;
import br.com.arbo.steamside.app.injection.Container;

class DifferentUser implements Part {

	final static String DIFFERENT_USER = "kid";

	@Override
	public void apply(final Container c)
	{
		c.replaceComponent(User.class, mockDifferentUser());
	}

	private static User mockDifferentUser()
	{
		final User user = Mockito.mock(User.class);
		Mockito.when(user.username()).thenReturn(DIFFERENT_USER);
		return user;
	}

}