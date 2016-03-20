package br.com.arbo.steamside.steam.client.localfiles.appcache.parse;

import br.com.arbo.steamside.steam.client.localfiles.appcache.DumpAppCacheParse;
import br.com.arbo.steamside.steam.client.localfiles.appcache.File_appinfo_vdf_Factory;

public class ExampleDumpAppCacheParse
{

	public static void main(final String[] args)
	{
		new DumpAppCacheParse(
			File_appinfo_vdf_Factory.fromSteamPhysicalFiles())
				.dump(System.out::println);
	}

}
