package br.com.arbo.steamside.app.main;

import br.com.arbo.steamside.app.context.SourcesFactory;
import br.com.arbo.steamside.app.context.SpringApplicationFactory;

public class Main
{

	public static void main(String... args) throws Exception
	{
		SpringApplicationFactory.run(SourcesFactory.newInstance(), args);
	}

}
