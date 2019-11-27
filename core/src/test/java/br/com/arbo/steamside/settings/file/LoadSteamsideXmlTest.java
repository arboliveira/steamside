package br.com.arbo.steamside.settings.file;

import java.io.ByteArrayInputStream;

import org.junit.Test;

import br.com.arbo.steamside.settings.xml.SteamsideXml_Unmarshal;

@SuppressWarnings("static-method")
public class LoadSteamsideXmlTest
{

	@Test
	public void unmarshal()
	{
		SteamsideXml_Unmarshal.unmarshal(
			new ByteArrayInputStream("<steamside/>".getBytes()));
	}

}
