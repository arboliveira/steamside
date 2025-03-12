package br.com.arbo.steamside.steam.client.localfiles.appinfo;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.fail;

import java.io.FileInputStream;
import java.util.HashMap;
import java.util.Map;

import br.com.arbo.steamside.steam.client.localfiles.copied_from_steam_directory.SteamLocationCopiedFromSteamDirectory;
import br.com.arbo.steamside.steam.client.localfiles.steamlocation.SteamLocation;
import org.junit.Test;

import br.com.arbo.steamside.steam.client.apps.Platform;
import br.com.arbo.steamside.steam.client.localfiles.vdf.KeyValueVisitor;

public class Content_appinfo_vdfTest
{

	@Test
	public void idKnown_nameKnown() throws Exception
	{
		SteamLocation steamLocation = new SteamLocationCopiedFromSteamDirectory();
        try (FileInputStream in = new FileInputStream(
			new File_appinfo_vdf(steamLocation).appinfo_vdf()))
		{
			ContentVisitor contentVisitor =
				new ContentVisitor(this::assert_app_id);

			KeyValueVisitor keyValueVisitor = contentVisitor;

			new Content_appinfo_vdf(in).accept(
				contentVisitor, keyValueVisitor);

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
			v -> assertThat(appinfo.name().name(), equalTo(v)));
	}

	private static void assertSuperHexagon(final AppInfo appinfo)
	{
		assertThat(appinfo.name().name(), equalTo("Super Hexagon"));
		Map<String, String> expected_executables = executablesSuperHexagon();
		assertThat(appinfo.executables(), equalTo(expected_executables));
	}

	private static Map<String, String> executablesSuperHexagon()
	{
		return new HashMap<String, String>()
		{

			{
				put(Platform.windows, "superhexagon.exe");
				put(Platform.linux, "./SuperHexagon");
				put(Platform.macos, "Super Hexagon.app");
			}
		};
	}

	private final KnownIdVsName id_vs_name = new KnownIdVsName();
}
