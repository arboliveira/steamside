package br.com.arbo.steamside.steam.client.localfiles.appinfo;

import java.util.function.Consumer;

import org.junit.Test;

public class ExampleDumpAppNamesGivenIds
{

	public static void main(final String[] args)
	{
		new ExampleDumpAppNamesGivenIds().run();
	}

	void run()
	{
		new DumpAppNamesGivenIds(
			File_appinfo_vdf_Factory.fromSteamPhysicalFiles())
				.dump(
					print,
					"22000", "9050", "12800", "10150", "35460", "204560");
	}

	private Consumer<String> print = System.out::println;

	@Test
	public void test()
	{
		print = null;

		run();
	}

}
