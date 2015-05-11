package br.com.arbo.steamside.app.main;

import org.springframework.stereotype.Component;

import br.com.arbo.org.springframework.boot.builder.Sources;
import br.com.arbo.steamside.app.launch.SourcesCustomizer;

public class ExampleRunSteamsideForTheFirstTime {

	public static void main(final String[] args)
	{
		SpringApplicationFactory.buildWith(
			SourcesFactory.newInstance().sources(FirstRunCustomize.class))
			.run(args);
	}

	@Component
	public static class FirstRunCustomize implements SourcesCustomizer {

		@Override
		public void customize(Sources sources)
		{
			FirstRun.customize(sources);
		}

	}

}
