package br.com.arbo.steamside.collections.system;

import java.util.stream.Stream;

import br.com.arbo.steamside.collections.InMemoryCollectionsHome;
import br.com.arbo.steamside.collections.Tag;
import br.com.arbo.steamside.data.collections.NotFound;
import br.com.arbo.steamside.library.Libraries;
import br.com.arbo.steamside.library.Library;
import br.com.arbo.steamside.settings.file.SteamsideData_ForExamples;
import br.com.arbo.steamside.types.AppId;
import br.com.arbo.steamside.types.CollectionName;

public class ExampleUncollected {

	public static void main(final String[] args) throws NotFound
	{
		new ExampleUncollected().run();
	}

	void printTag(Tag tag) throws br.com.arbo.steamside.apps.NotFound
	{
		AppId appid = tag.appid();

		System.out.println(library.find(appid));
	}

	void run()
	{
		final Stream< ? extends Tag> apps;
		SystemCollectionsHome sys =
				new SystemCollectionsHome(library, home);
		apps = sys.appsOf(new CollectionName("(uncollected)"));
		apps.forEach(this::printTag);
	}

	InMemoryCollectionsHome home = SteamsideData_ForExamples.fromXmlFile();

	Library library = Libraries.fromSteamPhysicalFiles();
}
