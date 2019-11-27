package br.com.arbo.steamside.collections;

import java.util.Optional;
import java.util.function.Consumer;

import org.junit.Test;

import br.com.arbo.steamside.data.collections.NotFound;
import br.com.arbo.steamside.settings.file.SteamsideData_ForExamples;
import br.com.arbo.steamside.steam.client.apps.App;
import br.com.arbo.steamside.steam.client.home.SteamClientHome;
import br.com.arbo.steamside.steam.client.library.Libraries;
import br.com.arbo.steamside.steam.client.types.AppId;
import br.com.arbo.steamside.types.CollectionName;

public class ExampleDumpSteamsideXmlOneCollection
{

	public static void main(final String[] args) throws NotFound
	{
		new ExampleDumpSteamsideXmlOneCollection().run();
	}

	private Consumer<Object> print = System.out::println;

	@Test
	public void test()
	{
		print = null;

		run();
	}

	void printTag(Tag tag)
	{
		AppId appid = tag.appid();

		Optional<App> find = library.apps().find(appid);

		if (print != null)
			print.accept(find);
	}

	void run()
	{
		final String name = "Hack n' Slash"; //"Unplayed";

		CollectionI collection = home.collections().find(
			new CollectionName(name));

		home.apps(collection).forEach(this::printTag);
	}

	InMemoryTagsHome home = SteamsideData_ForExamples.fromXmlFile();

	SteamClientHome library = Libraries.fromSteamPhysicalFiles();
}
