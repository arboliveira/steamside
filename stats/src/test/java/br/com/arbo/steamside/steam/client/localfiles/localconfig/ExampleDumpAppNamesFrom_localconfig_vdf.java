package br.com.arbo.steamside.steam.client.localfiles.localconfig;

import java.io.IOException;

import br.com.arbo.steamside.steam.client.localfiles.appcache.File_appinfo_vdf;
import br.com.arbo.steamside.steam.client.localfiles.sharedconfig.Dirs_userid;
import br.com.arbo.steamside.steam.client.localfiles.steamlocation.SteamLocations;

class ExampleDumpAppNamesFrom_localconfig_vdf
{

	public static void main(final String[] args) throws IOException
	{
		new DumpAppNamesFrom_localconfig_vdf(
			new File_localconfig_vdf(
				Dirs_userid.fromSteamPhysicalFiles()),
			new File_appinfo_vdf(
				SteamLocations.fromSteamPhysicalFiles()))
					.dump(
						System.out::println);
	}

}
