package br.com.arbo.steamside.steam.client.localfiles.sharedconfig;

public class File_sharedconfig_vdf_Factory
{

	public static File_sharedconfig_vdf fromSteamPhysicalFiles()
	{
		return new File_sharedconfig_vdf(
			Dirs_userid.fromSteamPhysicalFiles());
	}

}
