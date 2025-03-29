package br.com.arbo.steamside.steam.client.localfiles.sharedconfig;

import java.io.IOException;
import java.util.function.Consumer;

import org.junit.Ignore;
import org.junit.Test;

import br.com.arbo.steamside.steam.client.localfiles.appinfo.File_appinfo_vdf_Factory;

public class ExampleDumpAppNamesFrom_sharedconfig_vdf
{

	public static void main(final String[] args) throws IOException
	{
		new ExampleDumpAppNamesFrom_sharedconfig_vdf().run();
	}

	void run()
	{
		new DumpAppNamesFrom_sharedconfig_vdf(
			File_sharedconfig_vdf_Factory.fromSteamPhysicalFiles(),
			File_appinfo_vdf_Factory.fromSteamPhysicalFiles())
				.dump(print);
	}

	private Consumer<String> print = System.out::println;

	@Ignore("sharedconfig.vdf gone empty, file date 2025-03-16")
	@Test
	public void test()
	{
		print = null;

		run();
	}

}
