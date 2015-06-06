package br.com.arbo.steamside.app.main;

import org.springframework.stereotype.Component;

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
		SpringApplicationFactory
		.run(SourcesFactory.newInstance()
			.sources(DifferentUserCustomize.class), args);
	}

	@Component
	public static class DifferentUserCustomize implements SourcesCustomizer
	{

		@Override
		public void customize(Sources sources)
		{
			DifferentUser.customize(sources);
		}

	}

}
