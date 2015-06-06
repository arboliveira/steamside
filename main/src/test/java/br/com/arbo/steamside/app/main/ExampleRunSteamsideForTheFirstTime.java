package br.com.arbo.steamside.app.main;

import org.springframework.stereotype.Component;

import br.com.arbo.org.springframework.boot.builder.Sources;
import br.com.arbo.steamside.app.context.SourcesFactory;
import br.com.arbo.steamside.app.context.SpringApplicationFactory;
import br.com.arbo.steamside.app.launch.SourcesCustomizer;

public class ExampleRunSteamsideForTheFirstTime
{

	public static void main(final String[] args)
	{
		SpringApplicationFactory
			.run(
				SourcesFactory.newInstance().sources(FirstRunCustomize.class),
				args);
	}

	@Component
	public static class FirstRunCustomize implements SourcesCustomizer
	{

		@Override
		public void customize(Sources sources)
		{
			FirstRun.customize(sources);
		}

	}

}
