package br.com.arbo.steamside.steam.client.localfiles.appcache.parse;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.junit.Assert.assertThat;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.HashMap;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import br.com.arbo.steamside.steam.client.localfiles.appcache.Content_appinfo_vdf;
import br.com.arbo.steamside.steam.client.localfiles.appcache.File_appinfo_vdf;
import br.com.arbo.steamside.steam.client.localfiles.appcache.entry.AppInfo;

public class Parse_appinfo_vdfTest {

	private FileInputStream file;
	private Content_appinfo_vdf vdf;

	@Before
	public void open_appinfo_vdf() throws FileNotFoundException {
		file = new FileInputStream(File_appinfo_vdf.appinfo_vdf());
		vdf = new Content_appinfo_vdf(file);
	}

	@After
	public void close_appinfo_vdf() throws IOException {
		file.close();
	}

	@Test
	public void idKnown_nameKnown() {
		final HashMap<String, String> id_vs_name = id_vs_name();
		new Parse_appinfo_vdf(vdf,
				new ParseVisitor() {

					@Override
					public void each(final String appid,
							final AppInfo appinfo) {
						final String nameKnown = id_vs_name.get(appid);
						if (nameKnown != null)
							assertThat(appinfo.name().name, equalTo(nameKnown));
					}
				}).parse();
	}

	private static HashMap<String, String> id_vs_name() {
		final HashMap<String, String> m = new HashMap<String, String>();
		m.put("6870", "Battlestations: Midway");
		m.put("12210", "Grand Theft Auto IV");
		m.put("55230", "Saints Row: The Third");
		return m;
	}
}
