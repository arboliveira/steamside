package br.com.arbo.steamside.steam.client.localfiles.steamlocation;

import org.apache.commons.lang3.SystemUtils;

import br.com.arbo.opersys.userhome.FromSystemUtils;
import br.com.arbo.opersys.userhome.FromWindowsUtils;

public class SteamLocations {

	public static SteamLocation fromSteamPhysicalFiles()
	{
		if (SystemUtils.IS_OS_LINUX)
			return new Linux(new FromSystemUtils());
		if (SystemUtils.IS_OS_WINDOWS)
			return new Windows(new FromWindowsUtils());
		if (SystemUtils.IS_OS_MAC_OSX)
			return new MacOSX(new FromSystemUtils());
		throw new UnsupportedOperationException();
	}

}
