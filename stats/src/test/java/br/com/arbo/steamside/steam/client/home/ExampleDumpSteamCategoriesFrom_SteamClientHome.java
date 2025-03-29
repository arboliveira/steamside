package br.com.arbo.steamside.steam.client.home;

import java.util.function.Consumer;

import br.com.arbo.steamside.steam.client.library.Libraries;
import org.junit.Test;

public class ExampleDumpSteamCategoriesFrom_SteamClientHome
{

	public static void main(final String[] args)
	{
		new ExampleDumpSteamCategoriesFrom_SteamClientHome().run();
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
		new DumpSteamCategoriesFrom_SteamClientHome(
			Libraries.fromSteamPhysicalFiles())
				.dump(print);
	}

}
