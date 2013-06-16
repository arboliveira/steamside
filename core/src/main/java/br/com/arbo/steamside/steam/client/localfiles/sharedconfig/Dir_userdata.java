package br.com.arbo.steamside.steam.client.localfiles.sharedconfig;

import java.io.File;

import br.com.arbo.steamside.steam.client.localfiles.SteamDirectory;

public class Dir_userdata {

	public static File userdata() {
		final File steam = SteamDirectory.steam();
		return new File(steam, "userdata");
	}
}
