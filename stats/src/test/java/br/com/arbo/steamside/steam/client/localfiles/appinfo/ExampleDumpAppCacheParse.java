package br.com.arbo.steamside.steam.client.localfiles.appinfo;

import java.util.function.Consumer;

import org.junit.Test;

public class ExampleDumpAppCacheParse
{

	public static void main(final String[] args)
	{
		new ExampleDumpAppCacheParse().run();
	}

	private Consumer<String> print = System.out::println;

	@Test
	public void test()
	{
		print = null;

		run();
	}

	void run()
	{
		new DumpAppinfoVdfParse(
			File_appinfo_vdf_Factory.fromSteamPhysicalFiles())
				.dump(print);
	}

}
