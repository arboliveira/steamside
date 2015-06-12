package br.com.arbo.steamside.demo.context;

import org.springframework.stereotype.Component;

import br.com.arbo.org.springframework.boot.builder.Sources;
import br.com.arbo.steamside.api.session.SessionController_session;
import br.com.arbo.steamside.app.launch.SourcesCustomizer;
import br.com.arbo.steamside.steam.client.apps.AppsHomeFactory;

@Component
public class DemoSubstitutions implements SourcesCustomizer
{

	@Override
	public void customize(Sources sources)
	{
		DemoLocalSettingsFactory.customize(sources);

		AppsHomeFactory demoAppsHomeFactory =
			DemoAppsHome.demoAppsHomeFactory();

		sources.replaceWithSingleton(
			AppsHomeFactory.class, demoAppsHomeFactory);

		DemoSteamsideData.customize(sources, demoAppsHomeFactory);

		sources.replaceWithImplementor(
			SessionController_session.class, DemoSession.class);
	}

}