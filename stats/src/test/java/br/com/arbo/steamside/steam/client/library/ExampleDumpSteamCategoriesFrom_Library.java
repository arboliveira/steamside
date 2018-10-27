package br.com.arbo.steamside.steam.client.library;

import br.com.arbo.steamside.steam.client.home.DumpSteamCategoriesFrom_SteamClientHome;

public class ExampleDumpSteamCategoriesFrom_Library
{

	public static void main(final String[] args)
	{
		new DumpSteamCategoriesFrom_SteamClientHome(
			Libraries.fromSteamPhysicalFiles())
				.dump(System.out::println);
	}

}
