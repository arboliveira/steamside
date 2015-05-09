package br.com.arbo.steamside.app.main;

import br.com.arbo.steamside.app.injection.SpringApplicationFactory;

public class Main {

	public static void main(final String... args) throws Exception
	{
		SpringApplicationFactory.buildWith(SourcesFactory.newInstance())
			.run(args);
	}

}
