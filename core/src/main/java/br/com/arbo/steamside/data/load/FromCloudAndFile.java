package br.com.arbo.steamside.data.load;

import java.io.FileNotFoundException;
import java.util.Optional;

import javax.inject.Inject;

import br.com.arbo.steamside.bootstrap.Bootstrap;
import br.com.arbo.steamside.cloud.CloudSettingsFactory.Missing;
import br.com.arbo.steamside.cloud.LoadCloud;
import br.com.arbo.steamside.cloud.LoadCloud.Disabled;
import br.com.arbo.steamside.cloud.Unavailable;
import br.com.arbo.steamside.data.InMemorySteamsideData;
import br.com.arbo.steamside.data.SteamsideData;
import br.com.arbo.steamside.firstrun.InitialLoadDetectedFirstRunEver;
import br.com.arbo.steamside.settings.file.LoadFile;

public class FromCloudAndFile implements InitialLoad
{

	@Inject
	public FromCloudAndFile(
		LoadCloud cloud, LoadFile file, Bootstrap bootstrap)
	{
		this.cloud = cloud;
		this.file = file;
		this.bootstrap = bootstrap;
	}

	@Override
	public SteamsideData loadSteamsideData()
	{
		Optional<SteamsideData> fromCloud = fromCloud();
		if (fromCloud.isPresent())
			return fromCloud.get();

		Optional<SteamsideData> fromFile = fromFile();
		if (fromFile.isPresent())
			return fromFile.get();

		bootstrap.fireEvent(new InitialLoadDetectedFirstRunEver());

		return InMemorySteamsideData.newInstance();
	}

	private Optional<SteamsideData> fromCloud()
	{
		try
		{
			return Optional.of(cloud.load().toSteamsideData());
			// TODO Success? Enqueue save to file
		}
		catch (Disabled e)
		{
			// Oh well, you know what you're doing, probably
			return Optional.empty();
		}
		catch (Unavailable e)
		{
			// TODO Send Warning to User Alert Bus: can't sync to the cloud
			e.printStackTrace();
			return Optional.empty();
		}
		catch (Missing e)
		{
			// TODO Send Suggestion to User Alert Bus: configure sync?
			e.printStackTrace();
			return Optional.empty();
		}
	}

	private Optional<SteamsideData> fromFile()
	{
		try
		{
			return Optional.of(file.load().toSteamsideData());
		}
		catch (FileNotFoundException e)
		{
			// TODO Notify the outside world. First time?
			e.printStackTrace();
			return Optional.empty();
		}
	}

	private final Bootstrap bootstrap;
	private final LoadCloud cloud;
	private final LoadFile file;
}
