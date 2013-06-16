package br.com.arbo.steamside.steam.client.localfiles.appcache;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.HashMap;

import br.com.arbo.steamside.steam.client.localfiles.appcache.Parse_appinfo_vdf.ParseVisitor;

public class InMemory_appinfo_vdf {

	public AppInfo get(final String appid) throws NotFound {
		final AppInfo appInfo = map.get(appid);
		if (appInfo == null) throw NotFound.appid(appid);
		return appInfo;
	}

	public static class NotFound extends Exception {

		public static NotFound appid(final String appid) {
			return new NotFound("appinfo.vdf missing: " + appid);
		}

		private NotFound(final String message) {
			super(message);
		}
	}

	final HashMap<String, AppInfo> map;

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
			new Parse_appinfo_vdf(new Content_appinfo_vdf(f),
					new ParseVisitor() {

						@Override
						public void each(final String appid,
								final AppInfo appinfo) {
							map.put(appid, appinfo);
						}
					}).parse();
		} finally {
			f.close();
		}
	}
}
