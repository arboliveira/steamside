package br.com.arbo.steamside.steam.client.localfiles.appcache.inmemory;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.HashMap;

import javax.inject.Inject;

import br.com.arbo.steamside.steam.client.apps.MissingFrom_appinfo_vdf;
import br.com.arbo.steamside.steam.client.localfiles.appcache.Content_appinfo_vdf;
import br.com.arbo.steamside.steam.client.localfiles.appcache.File_appinfo_vdf;
import br.com.arbo.steamside.steam.client.localfiles.appcache.entry.AppInfo;
import br.com.arbo.steamside.steam.client.localfiles.appcache.parse.ParseVisitor;
import br.com.arbo.steamside.steam.client.localfiles.appcache.parse.Parse_appinfo_vdf;
import br.com.arbo.steamside.steam.client.types.AppId;

public class InMemory_appinfo_vdf implements Data_appinfo_vdf {

	private final File_appinfo_vdf file_appinfo_vdf;

	@Override
	public AppInfo get(final AppId appid) throws MissingFrom_appinfo_vdf {
		final AppInfo appInfo = map.get(appid.appid);
		if (appInfo == null) throw MissingFrom_appinfo_vdf.appid(appid);
		return appInfo;
	}

	@Inject
	public InMemory_appinfo_vdf(final File_appinfo_vdf file_appinfo_vdf) {
		this.file_appinfo_vdf = file_appinfo_vdf;
		this.map = new HashMap<String, AppInfo>();
		try {
			populate();
		} catch (final IOException e) {
			throw new RuntimeException(e);
		}
	}

	private void populate() throws IOException {
		final FileInputStream f = open_File_appinfo_vdf();
		try {
			populateFrom(new Content_appinfo_vdf(f));
		} finally {
			f.close();
		}
	}

	private FileInputStream open_File_appinfo_vdf() {
		try {
			return new FileInputStream(file_appinfo_vdf.appinfo_vdf());
		} catch (final FileNotFoundException e) {
			throw new SteamNotInstalled(e);
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