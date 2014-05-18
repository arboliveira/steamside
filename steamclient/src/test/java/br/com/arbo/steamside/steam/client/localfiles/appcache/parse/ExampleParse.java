package br.com.arbo.steamside.steam.client.localfiles.appcache.parse;

import java.io.FileInputStream;
import java.io.IOException;

import br.com.arbo.steamside.steam.client.localfiles.appcache.Content_appinfo_vdf;
import br.com.arbo.steamside.steam.client.localfiles.appcache.File_appinfo_vdf;
import br.com.arbo.steamside.steam.client.localfiles.appcache.entry.AppInfo;
import br.com.arbo.steamside.steam.client.localfiles.steamlocation.SteamLocations;

public class ExampleParse {

	public static void main(final String[] args) throws IOException {
		final FileInputStream f =
				new FileInputStream(
						new File_appinfo_vdf(SteamLocations
								.fromSteamPhysicalFiles()).appinfo_vdf());
		try {
			new Parse_appinfo_vdf(new Content_appinfo_vdf(f),
					new ParseVisitor() {

						@Override
						public void each(final String appid,
								final AppInfo appinfo) {
							System.out.println(appid + "=" + appinfo);
						}
					}).parse();
		} finally {
			f.close();
		}
	}
}
