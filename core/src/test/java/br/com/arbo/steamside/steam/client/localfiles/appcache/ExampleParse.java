package br.com.arbo.steamside.steam.client.localfiles.appcache;

import java.io.FileInputStream;
import java.io.IOException;

import br.com.arbo.steamside.steam.client.localfiles.appcache.Parse_appinfo_vdf.ParseVisitor;

public class ExampleParse {

	public static void main(final String[] args) throws IOException {
		final FileInputStream f =
				new FileInputStream(File_appinfo_vdf.appinfo_vdf());
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
