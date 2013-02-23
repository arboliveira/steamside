package br.com.arbo.steamside.steamclient.localfiles.steam.appcache;

import java.io.IOException;

import org.apache.commons.io.FileUtils;

public class InMemory_appinfo_vdf implements Content_appinfo_vdf {

	final String content = read_appinfo_vdf();

	@Override
	public String content() {
		return content;
	}

	private static String read_appinfo_vdf() {
		try {
			return FileUtils.readFileToString(File_appinfo_vdf.appinfo_vdf());
		} catch (final IOException e) {
			throw new RuntimeException(e);
		}
	}

}
