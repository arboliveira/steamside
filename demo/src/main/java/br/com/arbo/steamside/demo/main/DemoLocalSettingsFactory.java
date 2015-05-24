package br.com.arbo.steamside.demo.main;

import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.mock;

import br.com.arbo.org.springframework.boot.builder.Sources;
import br.com.arbo.steamside.settings.local.LocalSettings;
import br.com.arbo.steamside.settings.local.LocalSettingsFactory;

class DemoLocalSettingsFactory {

	static void customize(Sources sources)
	{
		sources
			.replaceWithSingleton(
				LocalSettingsFactory.class, demoLocalSettingsFactory());
	}

	private static LocalSettingsFactory demoLocalSettingsFactory()
	{
		LocalSettings localSettings = mock(LocalSettings.class);
		doReturn(false).when(localSettings).cloudEnabled();
		return () -> localSettings;
	}

}
