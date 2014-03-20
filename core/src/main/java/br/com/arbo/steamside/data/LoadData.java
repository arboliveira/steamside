package br.com.arbo.steamside.data;

import javax.inject.Inject;

import br.com.arbo.steamside.cloud.LoadCloud;
import br.com.arbo.steamside.data.autowire.AutowireSteamsideData;
import br.com.arbo.steamside.settings.file.LoadSteamsideXml;
import br.com.arbo.steamside.xml.SteamsideXml;

public class LoadData {

	@Inject
	public LoadData(
			AutowireSteamsideData data, LoadCloud cloud,
			LoadSteamsideXml xml) {
		this.data = data;
		this.cloud = cloud;
		this.xml = xml;
	}

	public void start()
	{
		SteamsideXml loaded = fromWherever();
		InMemorySteamsideData in = loaded.toSteamsideData();
		data.set(in);
	}

	private SteamsideXml fromWherever()
	{
		try {
			return cloud.load();
		}
		catch (RuntimeException e) {
			e.printStackTrace();
			return xml.load();
		}
	}

	private final LoadCloud cloud;

	private final AutowireSteamsideData data;

	private final br.com.arbo.steamside.settings.file.LoadSteamsideXml xml;
}
