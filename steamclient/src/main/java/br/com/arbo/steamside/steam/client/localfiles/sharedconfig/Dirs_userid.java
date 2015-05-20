package br.com.arbo.steamside.steam.client.localfiles.sharedconfig;

import br.com.arbo.steamside.steam.client.localfiles.steamlocation.SteamLocations;

public class Dirs_userid {

	public static Dir_userid fromSteamPhysicalFiles()
	{
		return new Dir_userid(new Dir_userdata(
			SteamLocations
				.fromSteamPhysicalFiles()));
	}

}
