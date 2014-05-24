package br.com.arbo.steamside.steam.client.localfiles.localconfig;

import java.io.IOException;
import java.io.InputStream;

import org.junit.Test;

public class Parse_localconfig_vdfTest {

	@Test
	public void apps_before_apptickets() throws IOException
	{
		InputStream in = this.getClass().getResourceAsStream(
				"apps_before_apptickets-appinfo.vdf");
		try {
			Parse_localconfig_vdf parse = new Parse_localconfig_vdf(in);
			parse.parse();
		}
		finally {
			in.close();
		}
	}
}
