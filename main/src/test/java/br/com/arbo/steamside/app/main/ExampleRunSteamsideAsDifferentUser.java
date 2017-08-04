package br.com.arbo.steamside.app.main;

import org.mockito.Mockito;

import br.com.arbo.opersys.username.User;
import br.com.arbo.org.springframework.boot.builder.Sources;
import br.com.arbo.steamside.app.context.SourcesFactory;
import br.com.arbo.steamside.app.context.SpringApplicationFactory;
import br.com.arbo.steamside.app.launch.SourcesCustomizer;

/**
 * Demonstrates Steamside will open on a different port
 * when launched under a different user.
 */
class ExampleRunSteamsideAsDifferentUser
{

	public static void main(final String[] args)
	{
		SpringApplicationFactory.run(
			SourcesFactory.newInstance().sources(DifferentUser.class),
			args);
	}

	static class DifferentUser implements SourcesCustomizer
	{

		private static User mockDifferentUser()
		{
			User user = Mockito.mock(User.class);
			Mockito.when(user.username()).thenReturn(DIFFERENT_USER);
			return user;
		}

		@Override
		public void customize(Sources s)
		{
			s.replaceWithSingleton(User.class, mockDifferentUser());
		}

		final static String DIFFERENT_USER = "kid";

	}

}
