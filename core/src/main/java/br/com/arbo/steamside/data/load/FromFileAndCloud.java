package br.com.arbo.steamside.data.load;

import java.io.FileNotFoundException;

import javax.inject.Inject;

import br.com.arbo.steamside.cloud.LoadCloud;
import br.com.arbo.steamside.cloud.LoadCloud.Disabled;
import br.com.arbo.steamside.cloud.Unavailable;
import br.com.arbo.steamside.data.InMemorySteamsideData;
import br.com.arbo.steamside.data.SteamsideData;
import br.com.arbo.steamside.settings.file.LoadSteamsideXml;
import br.com.arbo.steamside.xml.SteamsideXml;

public class FromFileAndCloud implements InitialLoad {

	private static SteamsideXml fromThinAir()
	{
		return new SteamsideXml();
	}

	@Inject
	public FromFileAndCloud(LoadCloud cloud, LoadSteamsideXml xml)
	{
		this.cloud = cloud;
		this.xml = xml;
	}

	@Override
	public SteamsideData loadSteamsideData()
	{
		SteamsideXml loaded = fromWherever();
		InMemorySteamsideData in = loaded.toSteamsideData();
		return in;
	}

	private SteamsideXml fromCloud()
	{
		try
		{
			return cloud.load();
		}
		catch (Disabled disabled)
		{
			// Oh well, you know what you're doing, probably  
			throw new NotLoaded();
		}
		catch (Unavailable unavailable)
		{
			// TODO Notify the outside world we could not sync with the cloud
			unavailable.printStackTrace();
			throw new NotLoaded();
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
			throw new NotLoaded();
		}
	}

	private SteamsideXml fromWherever()
	{
		try
		{
			return fromCloud();
		}
		catch (NotLoaded notFromCloud)
		{
			try
			{
				return fromFile();
			}
			catch (NotLoaded notFromFile)
			{
				return fromThinAir();
			}
		}
	}

	static class NotLoaded extends RuntimeException {
		//
	}

	private final LoadCloud cloud;

	private final LoadSteamsideXml xml;
}
