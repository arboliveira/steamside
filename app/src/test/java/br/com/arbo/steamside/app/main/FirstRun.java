package br.com.arbo.steamside.app.main;

import java.io.FileNotFoundException;

import javax.xml.bind.JAXB;

import br.com.arbo.org.springframework.boot.builder.Sources;
import br.com.arbo.steamside.cloud.CloudSettings;
import br.com.arbo.steamside.settings.file.LoadFile;
import br.com.arbo.steamside.settings.file.SaveFile;
import br.com.arbo.steamside.xml.SteamsideXml;

public class FirstRun {

	public static Sources customize(Sources s)
	{
		return s
			.replaceImplementor(CloudSettings.class, MockCloudSettings.class)
			.replaceImplementor(LoadFile.class, MockLoadFile.class)
			.replaceImplementor(SaveFile.class, MockSaveFile.class);
	}

	static class MockCloudSettings implements CloudSettings {

		@Override
		public boolean isEnabled()
		{
			return false;
		}

	}

	static class MockLoadFile implements LoadFile {

		@Override
		public SteamsideXml load() throws FileNotFoundException
		{
			throw new FileNotFoundException();
		}

	}

	static class MockSaveFile implements SaveFile {

		@Override
		public void save(SteamsideXml xml)
		{
			JAXB.marshal(xml, System.out);
		}

	}

}
