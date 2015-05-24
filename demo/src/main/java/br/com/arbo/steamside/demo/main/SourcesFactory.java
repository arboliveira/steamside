package br.com.arbo.steamside.demo.main;

import br.com.arbo.org.springframework.boot.builder.Sources;

public class SourcesFactory {

	public static Sources newInstance()
	{
		return br.com.arbo.steamside.app.main.SourcesFactory.newInstance()
			.sources(DemoSubstitutions.class);
	}
}
