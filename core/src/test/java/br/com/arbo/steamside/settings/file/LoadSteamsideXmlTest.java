package br.com.arbo.steamside.settings.file;

import java.io.ByteArrayInputStream;

import org.junit.Test;

@SuppressWarnings("static-method")
public class LoadSteamsideXmlTest {

	@Test
	public void unmarshal()
	{
		LoadSteamsideXml.unmarshal(
				new ByteArrayInputStream("<steamside/>".getBytes()));
	}

}
