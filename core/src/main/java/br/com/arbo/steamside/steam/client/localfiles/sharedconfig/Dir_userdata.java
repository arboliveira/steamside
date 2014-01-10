package br.com.arbo.steamside.steam.client.localfiles.sharedconfig;

import java.io.File;

import javax.inject.Inject;

import br.com.arbo.steamside.steam.client.localfiles.steamlocation.SteamLocation;

public class Dir_userdata {

	private final SteamLocation steamDirectory;

	@Inject
	public Dir_userdata(final SteamLocation steamDirectory) {
		this.steamDirectory = steamDirectory;
	}

	public File userdata() {
		final File steam = steamDirectory.steam();
		return new File(steam, "userdata");
	}
}
