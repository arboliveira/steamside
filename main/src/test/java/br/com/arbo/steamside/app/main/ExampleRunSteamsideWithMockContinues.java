package br.com.arbo.steamside.app.main;

import org.springframework.stereotype.Component;

import br.com.arbo.org.springframework.boot.builder.Sources;
import br.com.arbo.steamside.app.context.SourcesFactory;
import br.com.arbo.steamside.app.context.SpringApplicationFactory;
import br.com.arbo.steamside.app.launch.SourcesCustomizer;

public class ExampleRunSteamsideWithMockContinues {

	public static void main(final String[] args)
	{
		SpringApplicationFactory
			.buildWith(
				SourcesFactory.newInstance()
					.sources(MockContinuesCustomize.class))
			.run(args);
	}

	@Component
	public static class MockContinuesCustomize implements SourcesCustomizer {

		@Override
		public void customize(Sources sources)
		{
			MockContinues.customize(sources);
		}

	}

}
