package br.com.arbo.steamside.app.main;

import org.springframework.stereotype.Component;

import br.com.arbo.org.springframework.boot.builder.Sources;
import br.com.arbo.steamside.app.context.SourcesFactory;
import br.com.arbo.steamside.app.context.SpringApplicationFactory;
import br.com.arbo.steamside.app.launch.SourcesCustomizer;

class ExampleRunSteamsideInKidsMode {

	public static void main(final String[] args)
	{
		SpringApplicationFactory
			.buildWith(
				SourcesFactory.newInstance()
					.sources(KidsModeActiveCustomize.class))
			.run(args);
	}

	@Component
	public static class KidsModeActiveCustomize implements SourcesCustomizer {

		@Override
		public void customize(Sources sources)
		{
			KidsModeActive.customize(sources);
		}

	}

}
