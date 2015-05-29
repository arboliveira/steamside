package br.com.arbo.steamside.data.load;

import java.io.FileNotFoundException;
import java.util.Optional;

import javax.inject.Inject;

import br.com.arbo.steamside.cloud.CloudSettingsFactory.Missing;
import br.com.arbo.steamside.cloud.LoadCloud;
import br.com.arbo.steamside.cloud.LoadCloud.Disabled;
import br.com.arbo.steamside.cloud.Unavailable;
import br.com.arbo.steamside.collections.InMemoryCollectionsHome;
import br.com.arbo.steamside.collections.InMemoryTagsHome;
import br.com.arbo.steamside.data.InMemorySteamsideData;
import br.com.arbo.steamside.data.SteamsideData;
import br.com.arbo.steamside.data.SteamsideDataExecutor;
import br.com.arbo.steamside.kids.InMemoryKids;
import br.com.arbo.steamside.settings.file.LoadFile;

public class FromCloudAndFile implements InitialLoad
{

	@Inject
	public FromCloudAndFile(
		LoadCloud cloud, LoadFile file,
		SteamsideDataExecutor dataExecutor)
	{
		this.cloud = cloud;
		this.file = file;
		this.dataExecutor = dataExecutor;
	}

	@Override
	public SteamsideData loadSteamsideData()
	{
		return fromCloud().orElseGet(
			() -> fromFile().orElseGet(
				() -> fromSteam()));
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

	private SteamsideData fromSteam()
	{
		dataExecutor.enqueueCopyAllSteamCategories();
		InMemoryCollectionsHome c = new InMemoryCollectionsHome();
		InMemoryTagsHome t = new InMemoryTagsHome(c);
		InMemoryKids k = new InMemoryKids();
		InMemorySteamsideData d = new InMemorySteamsideData(c, t, k);
		return d;
	}

	private final LoadCloud cloud;

	private final SteamsideDataExecutor dataExecutor;

	private final LoadFile file;
}
