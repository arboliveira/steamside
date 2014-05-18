package br.com.arbo.steamside.steam.client.localfiles.appcache;

import java.io.File;

import javax.inject.Inject;

import br.com.arbo.steamside.steam.client.localfiles.steamlocation.SteamLocation;

public class File_appinfo_vdf {

	private final SteamLocation steamDirectory;

	@Inject
	public File_appinfo_vdf(SteamLocation steamDirectory) {
		this.steamDirectory = steamDirectory;
	}

	public File appinfo_vdf() {
		final File appcache = new File(steamDirectory.steam(), "appcache");
		return new File(appcache, "appinfo.vdf");
	}
}
