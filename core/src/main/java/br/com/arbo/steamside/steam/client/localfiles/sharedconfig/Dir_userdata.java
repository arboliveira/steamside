package br.com.arbo.steamside.steam.client.localfiles.sharedconfig;

import java.io.File;

import javax.inject.Inject;

import br.com.arbo.steamside.steam.client.localfiles.SteamDirectory;

public class Dir_userdata {

	private final SteamDirectory steamDirectory;

	@Inject
	public Dir_userdata(final SteamDirectory steamDirectory) {
		this.steamDirectory = steamDirectory;
	}

	public File userdata() {
		final File steam = steamDirectory.steam();
		return new File(steam, "userdata");
	}
}
