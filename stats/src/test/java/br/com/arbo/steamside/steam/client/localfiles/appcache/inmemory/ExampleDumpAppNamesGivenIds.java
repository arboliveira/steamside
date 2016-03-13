package br.com.arbo.steamside.steam.client.localfiles.appcache.inmemory;

import br.com.arbo.steamside.steam.client.localfiles.appcache.DumpAppNamesGivenIds;

class ExampleDumpAppNamesGivenIds
{

	public static void main(final String[] args)
	{
		new DumpAppNamesGivenIds(
			"22000", "9050", "12800", "10150", "35460", "204560")
				.dump(System.out::println);
	}
}
