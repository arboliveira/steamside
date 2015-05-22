package br.com.arbo.steamside.app.main;

import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.mock;

import java.io.FileNotFoundException;

import javax.xml.bind.JAXB;

import br.com.arbo.org.springframework.boot.builder.Sources;
import br.com.arbo.steamside.settings.file.LoadFile;
import br.com.arbo.steamside.settings.file.SaveFile;
import br.com.arbo.steamside.settings.local.LocalSettingsFactory;
import br.com.arbo.steamside.settings.local.LocalSettingsFactory.Missing;
import br.com.arbo.steamside.xml.SteamsideXml;

public class FirstRun {

	public static Sources customize(Sources s)
	{
		return s
			.replaceWithSingleton(
				LocalSettingsFactory.class, missingLocalSettings())
			.replaceWithImplementor(LoadFile.class, MockLoadFile.class)
			.replaceWithImplementor(SaveFile.class, MockSaveFile.class);
	}

	private static LocalSettingsFactory missingLocalSettings()
	{
		LocalSettingsFactory mock = mock(LocalSettingsFactory.class);

		doThrow(Missing.class).when(mock).read();

		return mock;
	}

	public static class MockLoadFile implements LoadFile {

		@Override
		public SteamsideXml load() throws FileNotFoundException
		{
			throw new FileNotFoundException();
		}

	}

	public static class MockSaveFile implements SaveFile {

		@Override
		public void save(SteamsideXml xml)
		{
			JAXB.marshal(xml, System.out);
		}

	}

}
