package br.com.arbo.steamside.steam.client.library;

import br.com.arbo.steamside.steam.client.home.SteamClientHome;
import br.com.arbo.steamside.steam.client.internal.home.SteamClientHomeFromLocalFiles;
import br.com.arbo.steamside.steam.client.localfiles.appinfo.File_appinfo_vdf;
import br.com.arbo.steamside.steam.client.localfiles.localconfig.File_localconfig_vdf;
import br.com.arbo.steamside.steam.client.localfiles.sharedconfig.Dir_userid;
import br.com.arbo.steamside.steam.client.localfiles.sharedconfig.Dirs_userid;
import br.com.arbo.steamside.steam.client.localfiles.sharedconfig.File_sharedconfig_vdf;
import br.com.arbo.steamside.steam.client.localfiles.steamlocation.SteamLocation;
import br.com.arbo.steamside.steam.client.localfiles.steamlocation.SteamLocations;

public class Libraries
{

	public static SteamClientHome fromSteamPhysicalFiles()
	{
		SteamLocation steamDir =
			SteamLocations.fromSteamPhysicalFiles();

		Dir_userid dir_userid = Dirs_userid.fromSteamPhysicalFiles();

		return new SteamClientHomeFromLocalFiles(
			new File_appinfo_vdf(steamDir),
			new File_localconfig_vdf(dir_userid),
			new File_sharedconfig_vdf(dir_userid)).hydrate().join();
	}

}
