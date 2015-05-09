package br.com.arbo.steamside.app.main;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import br.com.arbo.steamside.app.jetty.WebApplicationContextTweak;

class ExampleRunSteamsideInKidsMode {

	public static void main(final String[] args)
	{
		SpringApplicationBuilderXFactory.newInstance().replaceWithConfiguration(
			WebApplicationContextTweak.class, Singletons.class)
			.build().run(args);
	}

	@Configuration
	public static class Singletons {

		@Bean
		public static WebApplicationContextTweak tweak()
		{
			return new KidsModeActive();
		}
	}
}
