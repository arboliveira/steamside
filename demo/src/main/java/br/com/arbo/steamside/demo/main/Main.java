package br.com.arbo.steamside.demo.main;

import br.com.arbo.steamside.app.main.SpringApplicationFactory;

public class Main {

	public static void main(final String... args) throws Exception
	{
		SpringApplicationFactory.buildWith(SourcesFactory.newInstance())
			.run(args);
	}

}
