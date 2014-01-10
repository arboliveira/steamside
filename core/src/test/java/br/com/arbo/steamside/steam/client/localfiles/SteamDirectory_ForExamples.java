package br.com.arbo.steamside.steam.client.localfiles;

import org.apache.commons.lang3.SystemUtils;

import br.com.arbo.org.apache.commons.lang3.FromSystemUtils;
import br.com.arbo.org.apache.commons.lang3.FromWindowsUtils;
import br.com.arbo.steamside.steam.client.localfiles.steamlocation.Linux;
import br.com.arbo.steamside.steam.client.localfiles.steamlocation.MacOSX;
import br.com.arbo.steamside.steam.client.localfiles.steamlocation.SteamLocation;
import br.com.arbo.steamside.steam.client.localfiles.steamlocation.Windows;

public class SteamDirectory_ForExamples {

	public static SteamLocation fromSteamPhysicalFiles() {
		if (SystemUtils.IS_OS_LINUX)
			return new Linux(new FromSystemUtils());
		if (SystemUtils.IS_OS_WINDOWS)
			return new Windows(new FromWindowsUtils());
		if (SystemUtils.IS_OS_MAC_OSX)
			return new MacOSX(new FromSystemUtils());
		throw new UnsupportedOperationException();
	}

}
