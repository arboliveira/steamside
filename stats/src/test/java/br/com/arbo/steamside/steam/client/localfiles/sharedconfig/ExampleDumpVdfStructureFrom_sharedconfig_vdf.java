package br.com.arbo.steamside.steam.client.localfiles.sharedconfig;

import java.util.function.Consumer;

import org.junit.Test;

public final class ExampleDumpVdfStructureFrom_sharedconfig_vdf
{

	public static void main(final String[] args) throws Exception
	{
		new ExampleDumpVdfStructureFrom_sharedconfig_vdf().run();
	}

	void run()
	{
		new DumpVdfStructureFrom_sharedconfig_vdf(
			File_sharedconfig_vdf_Factory.fromSteamPhysicalFiles())
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