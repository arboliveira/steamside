package br.com.arbo.steamside.steam.client.localfiles.steamlocation;

import java.io.File;

import javax.inject.Inject;

import br.com.arbo.opersys.userhome.UserHome;

public class MacOSX implements SteamLocation {

	@Inject
	public MacOSX(final UserHome userhome2) {
		userhome = userhome2;
	}

	@Override
	public File steam() {
		return new File(parent(), "Steam");
	}

	private File parent() {
		return new File(userhome.getUserHome(),
				"Library/Application Support");
	}

	private final UserHome userhome;
}
