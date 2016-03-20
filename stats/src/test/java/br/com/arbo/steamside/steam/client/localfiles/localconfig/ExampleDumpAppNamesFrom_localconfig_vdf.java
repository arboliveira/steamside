package br.com.arbo.steamside.steam.client.localfiles.localconfig;

import java.io.IOException;

import br.com.arbo.steamside.steam.client.localfiles.appcache.File_appinfo_vdf_Factory;

class ExampleDumpAppNamesFrom_localconfig_vdf
{

	public static void main(final String[] args) throws IOException
	{
		new DumpAppNamesFrom_localconfig_vdf(
			File_localconfig_vdf_Factory.fromSteamPhysicalFiles(),
			File_appinfo_vdf_Factory.fromSteamPhysicalFiles())
				.dump(
					System.out::println);
	}

}
