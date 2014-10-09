package br.com.arbo.steamside.data.load;

import java.io.FileNotFoundException;

import javax.inject.Inject;

import br.com.arbo.steamside.cloud.LoadCloud;
import br.com.arbo.steamside.cloud.LoadCloud.Disabled;
import br.com.arbo.steamside.cloud.Unavailable;
import br.com.arbo.steamside.settings.file.LoadSteamsideXml;
import br.com.arbo.steamside.xml.SteamsideXml;

public class FromCloudAndFile implements InitialLoad {

	@Inject
	public FromCloudAndFile(LoadCloud cloud, LoadSteamsideXml xml)
	{
		this.cloud = cloud;
		this.xml = xml;
	}

	@Override
	public SteamsideXml loadSteamsideXml()
	{
		try
		{
			return fromCloud();
		}
		catch (NotLoaded notFromCloud)
		{
			return fromFile();
		}
	}

	private SteamsideXml fromCloud()
	{
		try
		{
			return cloud.load();
		}
		catch (Disabled e)
		{
			// Oh well, you know what you're doing, probably
			throw new NotLoaded(e);
		}
		catch (Unavailable e)
		{
			// TODO Notify the outside world we could not sync with the cloud
			e.printStackTrace();
			throw new NotLoaded(e);
		}
	}

	private SteamsideXml fromFile()
	{
		try
		{
			return xml.load();
		}
		catch (FileNotFoundException e)
		{
			// TODO Notify the outside world. First time?
			e.printStackTrace();
			throw new NotLoaded(e);
		}
	}

	private final LoadCloud cloud;

	private final LoadSteamsideXml xml;
}
