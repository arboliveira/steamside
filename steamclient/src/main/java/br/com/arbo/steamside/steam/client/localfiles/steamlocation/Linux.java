package br.com.arbo.steamside.steam.client.localfiles.steamlocation;

import java.io.File;

import javax.inject.Inject;

import br.com.arbo.opersys.userhome.UserHome;

public class Linux implements SteamLocation {

	@Inject
	public Linux(final UserHome userhome2) {
		userhome = userhome2;
	}

	@Override
	public File steam() {
		return new File(parent(), "steam");
	}

	private File parent() {
		return new File(userhome.getUserHome(), ".steam");
	}

	private final UserHome userhome;

}
