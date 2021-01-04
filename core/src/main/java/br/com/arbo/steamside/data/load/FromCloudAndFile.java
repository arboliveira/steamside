package br.com.arbo.steamside.data.load;

import java.util.Optional;

import javax.inject.Inject;

import br.com.arbo.steamside.bootstrap.Bootstrap;
import br.com.arbo.steamside.data.InMemorySteamsideData;
import br.com.arbo.steamside.data.SteamsideData;
import br.com.arbo.steamside.firstrun.InitialLoadDetectedFirstRunEver;
import br.com.arbo.steamside.settings.file.LoadFile;
import br.com.arbo.steamside.xml.SteamsideXml_To_InMemorySteamsideData;

public class FromCloudAndFile implements InitialLoad
{

	@Override
	public SteamsideData loadSteamsideData()
	{
		checkForUpdatesFromTheCloud();

		Optional<SteamsideData> fromFile = fromFile();
		if (fromFile.isPresent())
			return fromFile.get();

		bootstrap.fireEvent(new InitialLoadDetectedFirstRunEver());

		return InMemorySteamsideData.newInstance();
	}

	private void checkForUpdatesFromTheCloud()
	{
		// TODO check for updates from the cloud
	}

	private Optional<SteamsideData> fromFile()
	{
		try
		{
			return Optional.of(
				SteamsideXml_To_InMemorySteamsideData
					.toSteamsideData(loadFile.load()));
		}
		catch (br.com.arbo.steamside.settings.file.LoadFile.Missing e)
		{
			// TODO Notify the outside world. First time?
			e.printStackTrace();
			return Optional.empty();
		}
	}

	@Inject
	public FromCloudAndFile(LoadFile file, Bootstrap bootstrap)
	{
		this.loadFile = file;
		this.bootstrap = bootstrap;
	}

	private final Bootstrap bootstrap;
	private final LoadFile loadFile;
}
