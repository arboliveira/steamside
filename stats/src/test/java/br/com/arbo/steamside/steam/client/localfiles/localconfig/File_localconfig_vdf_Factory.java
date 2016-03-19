package br.com.arbo.steamside.steam.client.localfiles.localconfig;

import br.com.arbo.steamside.steam.client.localfiles.sharedconfig.Dirs_userid;

public class File_localconfig_vdf_Factory
{

	public static File_localconfig_vdf fromSteamPhysicalFiles()
	{
		return new File_localconfig_vdf(
			Dirs_userid.fromSteamPhysicalFiles());
	}

}
