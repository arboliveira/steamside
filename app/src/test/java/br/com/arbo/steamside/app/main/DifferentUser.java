package br.com.arbo.steamside.app.main;

import org.mockito.Mockito;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import br.com.arbo.opersys.username.User;
import br.com.arbo.org.springframework.boot.builder.Sources;

@Configuration
public class DifferentUser {

	public static Sources modify(Sources s)
	{
		return s.replaceWithConfiguration(User.class, DifferentUser.class);
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