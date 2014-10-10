package br.com.arbo.steamside.app.main;

import java.io.FileNotFoundException;

import br.com.arbo.steamside.app.injection.ContainerWeb;
import br.com.arbo.steamside.app.jetty.WebApplicationContextTweak;
import br.com.arbo.steamside.cloud.CloudSettings;
import br.com.arbo.steamside.settings.file.LoadFile;
import br.com.arbo.steamside.xml.SteamsideXml;

public class FirstRun implements WebApplicationContextTweak {

	@Override
	public void tweak(ContainerWeb cx)
	{
		cx.replaceComponent(CloudSettings.class, MockCloudSettings.class);
		cx.replaceComponent(LoadFile.class, MockLoadFile.class);
	}

	static class MockCloudSettings
			implements CloudSettings {

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

}
