package br.com.arbo.steamside.app.main;

import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.mock;

import java.io.FileNotFoundException;

import br.com.arbo.org.springframework.boot.builder.Sources;
import br.com.arbo.steamside.settings.file.LoadFile;
import br.com.arbo.steamside.settings.file.SaveFile;
import br.com.arbo.steamside.settings.local.LocalSettingsFactory;
import br.com.arbo.steamside.settings.local.LocalSettingsFactory.Missing;

class FirstRun
{

	static Sources customize(Sources s)
	{
		return s
			.replaceWithSingleton(
				LocalSettingsFactory.class, missingLocalSettings())
			.replaceWithSingleton(LoadFile.class, missingSteamsideXml())
			.replaceWithImplementor(SaveFile.class, SaveToSysout.class);
	}

	private static LocalSettingsFactory missingLocalSettings()
	{
		LocalSettingsFactory mock = mock(LocalSettingsFactory.class);
		doThrow(Missing.class).when(mock).read();
		return mock;
	}

	private static LoadFile missingSteamsideXml()
	{
		try
		{
			LoadFile mock = mock(LoadFile.class);
			doThrow(FileNotFoundException.class).when(mock).load();
			return mock;
		}
		catch (FileNotFoundException e)
		{
			throw new RuntimeException(e);
		}
	}

}
