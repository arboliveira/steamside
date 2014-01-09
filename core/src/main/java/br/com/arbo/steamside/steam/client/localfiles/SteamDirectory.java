package br.com.arbo.steamside.steam.client.localfiles;

import java.io.File;

import javax.inject.Inject;

import org.apache.commons.lang3.SystemUtils;

import br.com.arbo.org.apache.commons.lang3.UserHome;
import br.com.arbo.steamside.steam.client.localfiles.steamlocation.Linux;
import br.com.arbo.steamside.steam.client.localfiles.steamlocation.MacOSX;
import br.com.arbo.steamside.steam.client.localfiles.steamlocation.SteamLocation;
import br.com.arbo.steamside.steam.client.localfiles.steamlocation.Windows;

public class SteamDirectory {

	public File steam() {
		return location().steam();
	}

	private SteamLocation location() {
		if (SystemUtils.IS_OS_LINUX) return new Linux(this.userhome);
		if (SystemUtils.IS_OS_WINDOWS) return new Windows();
		if (SystemUtils.IS_OS_MAC_OSX) return new MacOSX(this.userhome);
		throw new UnsupportedOperationException();
	}

	private final UserHome userhome;

	@Inject
	public SteamDirectory(UserHome userhome) {
		this.userhome = userhome;
	}
}
