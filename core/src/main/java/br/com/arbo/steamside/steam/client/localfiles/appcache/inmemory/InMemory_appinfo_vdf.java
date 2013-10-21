package br.com.arbo.steamside.steam.client.localfiles.appcache.inmemory;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.HashMap;

import br.com.arbo.steamside.steam.client.localfiles.appcache.Content_appinfo_vdf;
import br.com.arbo.steamside.steam.client.localfiles.appcache.File_appinfo_vdf;
import br.com.arbo.steamside.steam.client.localfiles.appcache.entry.AppInfo;
import br.com.arbo.steamside.steam.client.localfiles.appcache.parse.ParseVisitor;
import br.com.arbo.steamside.steam.client.localfiles.appcache.parse.Parse_appinfo_vdf;

public class InMemory_appinfo_vdf {

	public AppInfo get(final String appid) throws NotFound {
		final AppInfo appInfo = map.get(appid);
		if (appInfo == null) throw NotFound.appid(appid);
		return appInfo;
	}

	public InMemory_appinfo_vdf() {
		this.map = new HashMap<String, AppInfo>();
		try {
			populate();
		} catch (final IOException e) {
			throw new RuntimeException(e);
		}
	}

	private void populate() throws IOException {
		final FileInputStream f =
				new FileInputStream(File_appinfo_vdf.appinfo_vdf());
		try {
			populateFrom(new Content_appinfo_vdf(f));
		} finally {
			f.close();
		}
	}

	private void populateFrom(final Content_appinfo_vdf content) {
		new Parse_appinfo_vdf(content,
				new ParseVisitor() {

					@Override
					public void each(final String appid,
							final AppInfo appinfo) {
						map.put(appid, appinfo);
					}
				}).parse();
	}

	final HashMap<String, AppInfo> map;

}
