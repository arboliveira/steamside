package br.com.arbo.steamside.steam.client.localfiles.appcache;

import java.io.FileInputStream;
import java.io.FileNotFoundException;

import br.com.arbo.steamside.steam.client.localfiles.steamlocation.SteamLocations;

public class File_appinfo_vdf_Factory
{

	public static FileInputStream newFileInputStream()
		throws FileNotFoundException
	{
		return new FileInputStream(
			new File_appinfo_vdf(
				SteamLocations.fromSteamPhysicalFiles()
			).appinfo_vdf());
	}

}
