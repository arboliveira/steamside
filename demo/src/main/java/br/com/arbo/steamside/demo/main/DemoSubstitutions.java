package br.com.arbo.steamside.demo.main;

import org.springframework.stereotype.Component;

import br.com.arbo.org.springframework.boot.builder.Sources;
import br.com.arbo.steamside.app.launch.SourcesCustomizer;

@Component
public class DemoSubstitutions implements SourcesCustomizer {

	@Override
	public void customize(Sources sources)
	{
		DemoLocalSettingsFactory.customize(sources);
		DemoAppsHome.customize(sources);
		DemoSteamsideData.customize(sources);
	}

}