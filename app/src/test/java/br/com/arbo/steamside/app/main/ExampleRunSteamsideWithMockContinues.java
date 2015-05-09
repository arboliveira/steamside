package br.com.arbo.steamside.app.main;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import br.com.arbo.steamside.app.injection.SpringApplicationFactory;
import br.com.arbo.steamside.app.jetty.WebApplicationContextTweak;

public class ExampleRunSteamsideWithMockContinues {

	public static void main(final String[] args)
	{
		SpringApplicationFactory
			.buildWith(SourcesFactory.newInstance().replaceWithConfiguration(
				WebApplicationContextTweak.class, Singletons.class))
			.run(args);
	}

	@Configuration
	public static class Singletons {

		@Bean
		public static WebApplicationContextTweak tweak()
		{
			return new MockContinues();
		}
	}
}
