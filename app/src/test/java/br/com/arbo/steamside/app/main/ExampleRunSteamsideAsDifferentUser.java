package br.com.arbo.steamside.app.main;

import br.com.arbo.steamside.app.injection.SpringApplicationFactory;

/**
 * Demonstrates Steamside will open on a different port
 * when launched under a different user.
 */
class ExampleRunSteamsideAsDifferentUser {

	public static void main(final String[] args)
	{
		SpringApplicationFactory
			.buildWith(DifferentUser.modify(SourcesFactory.newInstance()))
			.run(args);
	}
}
