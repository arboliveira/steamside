package br.com.arbo.steamside.steam.client.localfiles.appcache.parse;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.not;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.fail;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.List;

import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.SystemUtils;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import br.com.arbo.org.apache.commons.lang3.FromSystemUtils;
import br.com.arbo.steamside.steam.client.localfiles.SteamDirectory;
import br.com.arbo.steamside.steam.client.localfiles.appcache.Content_appinfo_vdf;
import br.com.arbo.steamside.steam.client.localfiles.appcache.File_appinfo_vdf;
import br.com.arbo.steamside.steam.client.localfiles.appcache.entry.AppInfo;

public class Parse_appinfo_vdfTest {

	private FileInputStream file;
	private Content_appinfo_vdf vdf;

	@Before
	public void open_appinfo_vdf() throws FileNotFoundException {
		file = new FileInputStream(new File_appinfo_vdf(new SteamDirectory(
				new FromSystemUtils())).appinfo_vdf());
		vdf = new Content_appinfo_vdf(file);
	}

	@After
	public void close_appinfo_vdf() throws IOException {
		file.close();
	}

	@Test
	public void idKnown_nameKnown() {
		final HashMap<String, String> id_vs_name = id_vs_name();
		assertThat(id_vs_name.size(), is(not(0)));
		new Parse_appinfo_vdf(vdf,
				new ParseVisitor() {

					@Override
					public void each(final String appid,
							final AppInfo appinfo) {
						assert_app_id(id_vs_name, appid, appinfo);
					}
				}).parse();
		if (id_vs_name.size() != 0)
			fail("Missing from appinfo.vdf: " + id_vs_name);
	}

	static void assert_app_id(final HashMap<String, String> id_vs_name,
			final String appid, final AppInfo appinfo) {
		if (appid.equals("221640")) {
			assertSuperHexagon(appinfo);
			return;
		}

		final String nameKnown = id_vs_name.remove(appid);
		if (nameKnown == null) return;
		assertThat(appinfo.name().name, equalTo(nameKnown));
	}

	private static void assertSuperHexagon(final AppInfo appinfo)
	{
		assertThat(appinfo.name().name, equalTo("Super Hexagon"));
		final String expected_executable =
				executableSuperHexagon();
		assertThat(appinfo.executable(), equalTo(expected_executable));
	}

	private static String executableSuperHexagon() {
		if (SystemUtils.IS_OS_WINDOWS)
			return "superhexagon.exe";
		if (SystemUtils.IS_OS_LINUX)
			return "./SuperHexagon";
		if (SystemUtils.IS_OS_MAC_OSX)
			return "Super Hexagon.app";
		throw new IllegalStateException();
	}

	private HashMap<String, String> id_vs_name() {
		final HashMap<String, String> m = new HashMap<String, String>();
		load_id_vs_name(m);
		return m;
	}

	private void load_id_vs_name(final HashMap<String, String> m) {
		try {
			load_id_vs_name_try(m);
		} catch (final IOException e) {
			throw new RuntimeException();
		}
	}

	private void load_id_vs_name_try(final HashMap<String, String> m)
			throws IOException {
		final InputStream in = this.getClass().getResourceAsStream(
				"known_id_vs_name.txt");
		try {
			load_id_vs_name_try(m, in);
		} finally {
			in.close();
		}
	}

	private static void load_id_vs_name_try(final HashMap<String, String> m,
			final InputStream in) throws IOException {
		final List<String> lines = IOUtils.readLines(in);
		for (final String line : lines) {
			final String[] strings = StringUtils.split(line, '\t');
			m.put(strings[0], strings[1]);
		}
	}
}
