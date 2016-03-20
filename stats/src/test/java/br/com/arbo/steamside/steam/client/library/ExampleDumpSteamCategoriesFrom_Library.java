package br.com.arbo.steamside.steam.client.library;

public class ExampleDumpSteamCategoriesFrom_Library
{

	public static void main(final String[] args)
	{
		new DumpSteamCategoriesFrom_Library(
			Libraries.fromSteamPhysicalFiles())
				.dump(System.out::println);
	}

}
