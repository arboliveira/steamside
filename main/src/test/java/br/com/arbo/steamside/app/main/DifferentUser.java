package br.com.arbo.steamside.app.main;

import org.mockito.Mockito;

import br.com.arbo.opersys.username.User;
import br.com.arbo.org.springframework.boot.builder.Sources;

public class DifferentUser
{

	public static Sources customize(Sources s)
	{
		return s.replaceWithSingleton(User.class, mockDifferentUser());
	}

	private static User mockDifferentUser()
	{
		User user = Mockito.mock(User.class);
		Mockito.when(user.username()).thenReturn(DIFFERENT_USER);
		return user;
	}

	final static String DIFFERENT_USER = "kid";

}