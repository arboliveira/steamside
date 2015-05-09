package br.com.arbo.steamside.app.main;

import org.mockito.Mockito;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import br.com.arbo.opersys.username.User;
import br.com.arbo.steamside.app.injection.SpringApplicationBuilderX;

@Configuration
public class DifferentUser {

	public static void apply(final SpringApplicationBuilderX c)
	{
		c.replaceWithConfiguration(User.class, DifferentUser.class);
	}

	@Bean
	public static User mockDifferentUser()
	{
		User user = Mockito.mock(User.class);
		Mockito.when(user.username()).thenReturn(DIFFERENT_USER);
		return user;
	}

	final static String DIFFERENT_USER = "kid";

}