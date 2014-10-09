package br.com.arbo.steamside.cloud;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;

import javax.inject.Inject;
import javax.xml.bind.JAXB;

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

	private static SteamsideXml toSteamsideXml(String xmlFromCloud)
	{
		final byte[] bytes = getBytes(xmlFromCloud);

		try (InputStream stream = new ByteArrayInputStream(bytes))
		{
			return unmarshal(stream);
		}
		catch (IOException e)
		{
			// never happens with a ByteArrayInputStream, "close" API fail 
			throw new RuntimeException(e);
		}
	}

	private static SteamsideXml unmarshal(final InputStream stream)
	{
		return JAXB.unmarshal(stream, SteamsideXml.class);
	}

	@Inject
	public LoadCloud(Cloud cloud, CloudSettings settings)
	{
		this.cloud = cloud;
		this.settings = settings;
	}

	public SteamsideXml load()
	{
		if (!settings.isEnabled()) throw new Disabled();

		String xml = cloud.download();

		return toSteamsideXml(xml);
	}

	public static class Disabled extends RuntimeException {
		//
	}

	private final Cloud cloud;

	private final CloudSettings settings;
}
