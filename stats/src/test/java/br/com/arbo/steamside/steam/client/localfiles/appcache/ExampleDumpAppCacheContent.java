package br.com.arbo.steamside.steam.client.localfiles.appcache;

import java.io.IOException;

class ExampleDumpAppCacheContent
{

	public static void main(final String[] args) throws IOException
	{
		new DumpAppCacheContent(
			File_appinfo_vdf_Factory.fromSteamPhysicalFiles())
				.dump(System.out::println);
	}

}
