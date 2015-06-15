package br.com.arbo.steamside.demo.context;

import static org.mockito.Mockito.mock;

import javax.xml.bind.JAXB;

import org.mockito.Mockito;

import br.com.arbo.org.springframework.boot.builder.Sources;
import br.com.arbo.steamside.settings.local.LocalSettingsFactory;
import br.com.arbo.steamside.settings.local.LocalSettingsPersistence;
import br.com.arbo.steamside.settings.local.SteamsideLocalXml;

class DemoLocalSettingsFactory
{

	static void customize(Sources sources)
	{
		sources.replaceWithSingleton(
			LocalSettingsFactory.class, demoLocalSettingsFactory());

		sources.replaceWithSingleton(
			LocalSettingsPersistence.class, demoLocalSettingsPersistence());
	}

	private static LocalSettingsFactory demoLocalSettingsFactory()
	{
		LocalSettingsFactory mock = mock(LocalSettingsFactory.class);
		Mockito.doThrow(LocalSettingsFactory.Missing.class).when(mock).read();
		return mock;
	}

	private static LocalSettingsPersistence demoLocalSettingsPersistence()
	{
		return new WriteToSysout();
	}

	public static class WriteToSysout implements LocalSettingsPersistence
	{

		@Override
		public void write(SteamsideLocalXml xml)
		{
			JAXB.marshal(xml, System.out);
		}

	}
}
