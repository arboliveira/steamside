package br.com.arbo.steamside.steam.client.localfiles.appcache;

import java.io.FileInputStream;
import java.io.FileNotFoundException;

import br.com.arbo.steamside.steam.client.localfiles.appinfo.File_appinfo_vdf;
import br.com.arbo.steamside.steam.client.localfiles.steamlocation.SteamLocations;

public class File_appinfo_vdf_Factory
{

	public static FileInputStream newFileInputStream()
		throws FileNotFoundException
	{
		return new FileInputStream(
			fromSteamPhysicalFiles().appinfo_vdf());
	}

	public static File_appinfo_vdf fromSteamPhysicalFiles()
	{
		return new File_appinfo_vdf(
			SteamLocations.fromSteamPhysicalFiles()
		);
	}

}
