package br.com.arbo.steamside.steam.client.library;

import java.util.function.Consumer;

import org.junit.Test;

import br.com.arbo.steamside.steam.client.home.DumpSteamCategoriesFrom_SteamClientHome;

public class ExampleDumpSteamCategoriesFrom_Library
{

	public static void main(final String[] args)
	{
		new ExampleDumpSteamCategoriesFrom_Library().run();
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
