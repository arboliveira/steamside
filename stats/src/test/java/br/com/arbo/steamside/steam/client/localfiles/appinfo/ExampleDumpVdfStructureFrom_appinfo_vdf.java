package br.com.arbo.steamside.steam.client.localfiles.appinfo;

import java.io.IOException;
import java.util.function.Consumer;

import org.junit.Test;

public class ExampleDumpVdfStructureFrom_appinfo_vdf
{

	public static void main(final String[] args) throws IOException
	{
		new ExampleDumpVdfStructureFrom_appinfo_vdf().run();
	}

	void run()
	{
		new DumpVdfStructureFrom_appinfo_vdf(
			File_appinfo_vdf_Factory.fromSteamPhysicalFiles())
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
