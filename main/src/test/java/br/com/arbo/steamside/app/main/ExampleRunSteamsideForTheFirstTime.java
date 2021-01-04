package br.com.arbo.steamside.app.main;

import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.mock;

import org.springframework.stereotype.Component;

import br.com.arbo.org.springframework.boot.builder.Sources;
import br.com.arbo.steamside.app.context.SourcesFactory;
import br.com.arbo.steamside.app.context.SpringApplicationFactory;
import br.com.arbo.steamside.app.launch.SourcesCustomizer;
import br.com.arbo.steamside.cloud.CopySteamsideXmlToCloud;
import br.com.arbo.steamside.settings.file.LoadFile;
import br.com.arbo.steamside.settings.file.SaveFile;
import br.com.arbo.steamside.settings.local.LocalSettingsFactory;
import br.com.arbo.steamside.settings.local.LocalSettingsFactory.Missing;

class ExampleRunSteamsideForTheFirstTime
{

	public static void main(final String[] args)
	{
		SpringApplicationFactory.run(
			SourcesFactory.newInstance().sources(FirstRunCustomize.class),
			args);
	}

	@Component
	public static class FirstRunCustomize implements SourcesCustomizer
	{

		private static LocalSettingsFactory missingLocalSettings()
		{
			LocalSettingsFactory mock = mock(LocalSettingsFactory.class);
			doThrow(Missing.class).when(mock).read();
			return mock;
		}

		private static LoadFile missingSteamsideXml()
		{
			LoadFile mock = mock(LoadFile.class);
			doThrow(br.com.arbo.steamside.settings.file.LoadFile.Missing.class)
				.when(mock).load();
			return mock;
		}

		@Override
		public void customize(Sources sources)
		{
			sources
				.replaceWithSingleton(
					LocalSettingsFactory.class, missingLocalSettings())
				.replaceWithSingleton(LoadFile.class, missingSteamsideXml())
				.replaceWithImplementor(SaveFile.class, SaveToSysout.class)
				.replaceWithImplementor(
					CopySteamsideXmlToCloud.class, UploadToSysout.class);
		}

	}

}
