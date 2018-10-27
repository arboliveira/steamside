package br.com.arbo.steamside.collections;

import java.util.stream.Stream;

import br.com.arbo.steamside.data.collections.NotFound;
import br.com.arbo.steamside.settings.file.SteamsideData_ForExamples;
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

	void printTag(Tag tag)
	{
		AppId appid = tag.appid();

		System.out.println(library.apps().find(appid));
	}

	void run()
	{
		final String name = "Hack n' Slash"; //"Unplayed";

		CollectionI collection = home.collections().find(
			new CollectionName(name));

		final Stream< ? extends Tag> apps = home.apps(collection);
		apps.forEach(this::printTag);
	}

	InMemoryTagsHome home = SteamsideData_ForExamples.fromXmlFile();

	SteamClientHome library = Libraries.fromSteamPhysicalFiles();
}
