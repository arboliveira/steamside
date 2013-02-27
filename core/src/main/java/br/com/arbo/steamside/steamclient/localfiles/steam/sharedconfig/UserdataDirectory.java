package br.com.arbo.steamside.steamclient.localfiles.steam.sharedconfig;

import java.io.File;

import br.com.arbo.steamside.steamclient.localfiles.steam.SteamDirectory;

public class UserdataDirectory {

	public static File userdata() {
		final File steam = SteamDirectory.steam();
		return new File(steam, "userdata");
	}
}
