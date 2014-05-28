package br.com.arbo.steamside.settings.file;

import java.util.stream.Stream;

import br.com.arbo.steamside.collections.CollectionI;
import br.com.arbo.steamside.collections.InMemoryCollectionsHome;
import br.com.arbo.steamside.collections.Tag;
import br.com.arbo.steamside.data.collections.NotFound;
import br.com.arbo.steamside.library.Libraries;
import br.com.arbo.steamside.library.Library;
import br.com.arbo.steamside.steam.client.types.AppId;
import br.com.arbo.steamside.types.CollectionName;

public class ExampleDumpOneCollection {

	public static void main(final String[] args) throws NotFound
	{
		new ExampleDumpOneCollection().run();
	}

	void printTag(Tag tag) throws br.com.arbo.steamside.steam.client.apps.NotFound
	{
		AppId appid = tag.appid();

		System.out.println(library.find(appid));
	}

	void run()
	{
		final Stream< ? extends Tag> apps;
		CollectionI collection =
				home.find(new CollectionName("Art"));
		apps = home.apps(collection);
		apps.forEach(this::printTag);
	}

	InMemoryCollectionsHome home = SteamsideData_ForExamples.fromXmlFile();

	Library library = Libraries.fromSteamPhysicalFiles();
}
