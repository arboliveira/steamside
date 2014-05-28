package br.com.arbo.steamside.app.main;

import org.mockito.Mockito;

import br.com.arbo.opersys.username.User;
import br.com.arbo.steamside.app.injection.Container;

class DifferentUser implements Part {

	@Override
	public void apply(final Container c)
	{
		c.replaceComponent(User.class, mockDifferentUser());
	}

	private static User mockDifferentUser()
	{
		final User user = Mockito.mock(User.class);
		Mockito.when(user.username()).thenReturn("kid");
		return user;
	}

	public static Part on()
	{
		return new DifferentUser();
	}

	public static Part off()
	{
		return null;
	}

}