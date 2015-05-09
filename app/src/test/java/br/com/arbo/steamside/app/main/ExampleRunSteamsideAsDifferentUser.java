package br.com.arbo.steamside.app.main;

import br.com.arbo.steamside.app.injection.SpringApplicationBuilderX;

/**
 * Demonstrates Steamside will open on a different port
 * when launched under a different user.
 */
class ExampleRunSteamsideAsDifferentUser {

	public static void main(final String[] args)
	{
		SpringApplicationBuilderX builder = SpringApplicationBuilderXFactory
			.newInstance();
		DifferentUser.apply(builder);
		builder.build().run(args);
	}
}
