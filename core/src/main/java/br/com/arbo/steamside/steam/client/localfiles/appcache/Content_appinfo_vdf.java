package br.com.arbo.steamside.steam.client.localfiles.appcache;

import java.io.IOException;

import org.apache.commons.io.FileUtils;

public class Content_appinfo_vdf {

	public static String content() {
		return read_appinfo_vdf();
	}

	private static String read_appinfo_vdf() {
		try {
			return FileUtils.readFileToString(File_appinfo_vdf.appinfo_vdf());
		} catch (final IOException e) {
			throw new RuntimeException(e);
		}
	}

}
