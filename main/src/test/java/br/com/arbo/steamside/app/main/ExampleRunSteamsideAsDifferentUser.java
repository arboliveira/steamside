package br.com.arbo.steamside.app.main;

import br.com.arbo.org.springframework.boot.builder.Sources;
import br.com.arbo.steamside.app.context.SourcesFactory;
import br.com.arbo.steamside.app.context.SpringApplicationFactory;

/**
 * Demonstrates Steamside will open on a different port
 * when launched under a different user.
 */
class ExampleRunSteamsideAsDifferentUser
{

	public static void main(final String[] args)
	{
		Sources sources = SourcesFactory.newInstance();

		DifferentUser.customize(sources);

		SpringApplicationFactory.run(sources, args);
	}

}
