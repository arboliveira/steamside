package br.com.arbo.steamside.cloud.http;

import java.io.ByteArrayInputStream;
import java.io.UnsupportedEncodingException;

import javax.inject.Inject;

import br.com.arbo.steamside.cloud.CloudSettings;
import br.com.arbo.steamside.cloud.CloudSettingsFactory;
import br.com.arbo.steamside.cloud.CloudSettingsFactory.Missing;
import br.com.arbo.steamside.settings.xml.SteamsideXml_Unmarshal;
import br.com.arbo.steamside.xml.SteamsideXml;

public class LoadCloud {

	private static byte[] getBytes(String xml)
	{
		try
		{
			return xml.getBytes("UTF-8");
		}
		catch (UnsupportedEncodingException e)
		{
			throw new RuntimeException(e);
		}
	}

	@Inject
	public LoadCloud(Cloud cloud, CloudSettingsFactory settings)
	{
		this.cloud = cloud;
		this.settingsFactory = settings;
	}

	public SteamsideXml load() throws Missing, Unavailable
	{
		CloudSettings read = settingsFactory.read();

		if (!read.isEnabled()) throw new Disabled();

		return SteamsideXml_Unmarshal.unmarshal(
			() -> new ByteArrayInputStream(getBytes(cloud.download())));
	}

	public static class Disabled extends RuntimeException {
		//
	}

	private final Cloud cloud;

	private final CloudSettingsFactory settingsFactory;
}
