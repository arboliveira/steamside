package br.com.arbo.steamside.steam.client.localfiles.localconfig;

import java.io.IOException;
import java.util.function.Consumer;

import org.junit.Test;

import br.com.arbo.steamside.steam.client.localfiles.appinfo.File_appinfo_vdf_Factory;

public class ExampleDumpAppNamesFrom_localconfig_vdf
{

	public static void main(final String[] args) throws IOException
	{
		new ExampleDumpAppNamesFrom_localconfig_vdf().run();
	}

	void run()
	{
		new DumpAppNamesFrom_localconfig_vdf(
			File_localconfig_vdf_Factory.fromSteamPhysicalFiles(),
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
