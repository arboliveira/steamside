package br.com.arbo.steamside.steam.client.localfiles.appcache.parse;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.fail;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

import org.apache.commons.lang3.SystemUtils;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import br.com.arbo.steamside.steam.client.localfiles.appcache.Content_appinfo_vdf;
import br.com.arbo.steamside.steam.client.localfiles.appcache.File_appinfo_vdf;
import br.com.arbo.steamside.steam.client.localfiles.appcache.entry.AppInfo;
import br.com.arbo.steamside.steam.client.localfiles.steamlocation.SteamLocations;

public class Parse_appinfo_vdfTest {

	private static void assertSuperHexagon(final AppInfo appinfo)
	{
		assertThat(appinfo.name().name, equalTo("Super Hexagon"));
		final String expected_executable = executableSuperHexagon();
		assertThat(appinfo.executable(), equalTo(expected_executable));
	}

	private static String executableSuperHexagon()
	{
		if (SystemUtils.IS_OS_WINDOWS)
			return "superhexagon.exe";
		if (SystemUtils.IS_OS_LINUX)
			return "./SuperHexagon";
		if (SystemUtils.IS_OS_MAC_OSX)
			return "Super Hexagon.app";
		throw new IllegalStateException();
	}

	@After
	public void close_appinfo_vdf() throws IOException
	{
		file.close();
	}

	@Test
	public void idKnown_nameKnown()
	{
		new Parse_appinfo_vdf(vdf, this::assert_app_id).parse();
		if (!id_vs_name.isEmpty())
			fail("Missing from appinfo.vdf: " + id_vs_name);
	}

	@Before
	public void open_appinfo_vdf() throws FileNotFoundException
	{
		file = new FileInputStream(
				new File_appinfo_vdf(SteamLocations
						.fromSteamPhysicalFiles()).appinfo_vdf());
		vdf = new Content_appinfo_vdf(file);
	}

	void assert_app_id(
			final String appid, final AppInfo appinfo)
	{
		if (appid.equals("221640"))
		{
			assertSuperHexagon(appinfo);
			return;
		}

		id_vs_name.remove(appid).ifPresent(
				v -> assertThat(appinfo.name().name, equalTo(v)));
	}

	private FileInputStream file;

	private Content_appinfo_vdf vdf;

	private final KnownIdVsName id_vs_name = new KnownIdVsName();
}
