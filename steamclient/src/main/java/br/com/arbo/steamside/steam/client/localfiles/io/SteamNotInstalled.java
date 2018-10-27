package br.com.arbo.steamside.steam.client.localfiles.io;

import java.io.FileNotFoundException;

public class SteamNotInstalled extends RuntimeException {

	public SteamNotInstalled(final FileNotFoundException e) {
		super(e);
	}

}
