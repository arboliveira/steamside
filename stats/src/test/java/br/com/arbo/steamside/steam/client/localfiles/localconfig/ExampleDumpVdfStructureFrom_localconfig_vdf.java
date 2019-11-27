package br.com.arbo.steamside.steam.client.localfiles.localconfig;

import java.util.function.Consumer;

import org.junit.Test;

public final class ExampleDumpVdfStructureFrom_localconfig_vdf
{

	public static void main(final String[] args) throws Exception
	{
		new ExampleDumpVdfStructureFrom_localconfig_vdf().run();
	}

	void run()
	{
		new DumpVdfStructureFrom_localconfig_vdf(
			File_localconfig_vdf_Factory.fromSteamPhysicalFiles())
				.dump(print);
	}

	private Consumer<String> print = System.out::println;

	@Test
	public void test()
	{
		print = null;

		run();
	}

}