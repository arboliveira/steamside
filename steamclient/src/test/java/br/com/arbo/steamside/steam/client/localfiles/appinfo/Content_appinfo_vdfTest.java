package br.com.arbo.steamside.steam.client.localfiles.appinfo;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.fail;

import java.io.FileInputStream;

import org.apache.commons.lang3.SystemUtils;
import org.junit.Test;

import br.com.arbo.steamside.steam.client.localfiles.appinfo.AppInfo;
import br.com.arbo.steamside.steam.client.localfiles.appinfo.ContentVisitor;
import br.com.arbo.steamside.steam.client.localfiles.appinfo.Content_appinfo_vdf;
import br.com.arbo.steamside.steam.client.localfiles.appinfo.File_appinfo_vdf;
import br.com.arbo.steamside.steam.client.localfiles.steamlocation.SteamLocations;

public class Content_appinfo_vdfTest
{

	@Test
	public void idKnown_nameKnown() throws Exception
	{
		try (FileInputStream in = new FileInputStream(
			new File_appinfo_vdf(SteamLocations
				.fromSteamPhysicalFiles()).appinfo_vdf()))
		{
			new Content_appinfo_vdf(in).accept(
				new ContentVisitor(this::assert_app_id));

			if (!id_vs_name.isEmpty())
				fail("Missing from appinfo.vdf: " + id_vs_name);
		}
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

	private final KnownIdVsName id_vs_name = new KnownIdVsName();
}
