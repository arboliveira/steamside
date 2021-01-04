package br.com.arbo.steamside.settings.xml;

import java.io.IOException;
import java.io.InputStream;
import java.util.function.Supplier;

import javax.xml.bind.JAXB;

import br.com.arbo.steamside.xml.SteamsideXml;

public class SteamsideXml_Unmarshal
{

	public static SteamsideXml unmarshal(InputStream in)
	{
		return JAXB.unmarshal(in, SteamsideXml.class);
	}

	public static SteamsideXml unmarshal(Supplier<InputStream> in)
	{
		try (InputStream stream = in.get())
		{
			return SteamsideXml_Unmarshal.unmarshal(stream);
		}
		catch (IOException e)
		{
			throw new RuntimeException(e);
		}
	}

}
