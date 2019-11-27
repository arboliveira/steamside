package br.com.arbo.steamside.steam.client.localfiles.localconfig;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.junit.Assert.assertThat;

import java.io.IOException;
import java.io.InputStream;

import org.junit.Test;

public class Parse_localconfig_vdfTest
{

	@Test
	public void apps_before_apptickets() throws IOException
	{
		Data_localconfig_vdf data = parse("apps_before_apptickets-appinfo.vdf");

		KV_apps apps = data.apps();
		assertThat(apps.all().count(), equalTo(2L));

		KV_apptickets apptickets = data.apptickets();
		assertThat(apptickets.all().count(), equalTo(2L));
	}

	private Data_localconfig_vdf parse(final String name) throws IOException
	{
		try (InputStream in = this.getClass().getResourceAsStream(name))
		{
			return new Parse_localconfig_vdf(in).parse();
		}
	}
}
