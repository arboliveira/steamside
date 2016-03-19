package br.com.arbo.steamside.steam.client.localfiles.sharedconfig;

import java.io.IOException;

import br.com.arbo.steamside.steam.client.localfiles.appcache.File_appinfo_vdf_Factory;

class ExampleDumpAppNamesFrom_sharedconfig_vdf
{

	public static void main(final String[] args) throws IOException
	{
		new DumpAppNamesFrom_sharedconfig_vdf(
			File_sharedconfig_vdf_Factory.fromSteamPhysicalFiles(),
			File_appinfo_vdf_Factory.fromSteamPhysicalFiles())
				.dump(System.out::println);
	}
}
