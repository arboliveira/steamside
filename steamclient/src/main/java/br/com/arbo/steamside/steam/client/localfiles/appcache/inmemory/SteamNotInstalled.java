package br.com.arbo.steamside.steam.client.localfiles.appcache.inmemory;

import java.io.FileNotFoundException;

public class SteamNotInstalled extends RuntimeException {

	public SteamNotInstalled(final FileNotFoundException e) {
		super(e);
	}

}
