package br.com.arbo.steamside.steamclient.localfiles.steam.appcache;

import java.io.File;

import br.com.arbo.steamside.steamclient.localfiles.steam.SteamDirectory;

public class File_appinfo_vdf {

	public static File appinfo_vdf() {
		final File appcache = new File(SteamDirectory.steam(), "appcache");
		return new File(appcache, "appinfo.vdf");
	}
}
