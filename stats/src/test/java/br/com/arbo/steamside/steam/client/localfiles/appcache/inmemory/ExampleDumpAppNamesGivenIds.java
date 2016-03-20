package br.com.arbo.steamside.steam.client.localfiles.appcache.inmemory;

import br.com.arbo.steamside.steam.client.localfiles.appcache.DumpAppNamesGivenIds;
import br.com.arbo.steamside.steam.client.localfiles.appcache.File_appinfo_vdf_Factory;

class ExampleDumpAppNamesGivenIds
{

	public static void main(final String[] args)
	{
		new DumpAppNamesGivenIds(
			File_appinfo_vdf_Factory.fromSteamPhysicalFiles())
				.dump(System.out::println,
					"22000", "9050", "12800", "10150", "35460", "204560");
	}
}
