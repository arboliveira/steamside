package br.com.arbo.steamside.steamclient.localfiles.steam;

import java.io.File;

public class SteamDirectory {

	public static File steam() {
		final File parent = SteamParentDirectory.parentdir();
		return new File(parent, "steam");
	}
}
