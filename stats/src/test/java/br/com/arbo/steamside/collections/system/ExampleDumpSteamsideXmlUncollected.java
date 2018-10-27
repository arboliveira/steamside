package br.com.arbo.steamside.collections.system;

import java.util.stream.Stream;

import br.com.arbo.steamside.collections.InMemoryTagsHome;
import br.com.arbo.steamside.collections.Tag;
import br.com.arbo.steamside.data.collections.NotFound;
import br.com.arbo.steamside.out.Out;
import br.com.arbo.steamside.settings.file.SteamsideData_ForExamples;
import br.com.arbo.steamside.steam.client.home.SteamClientHome;
import br.com.arbo.steamside.steam.client.library.Libraries;
import br.com.arbo.steamside.steam.client.types.AppId;

public class ExampleDumpSteamsideXmlUncollected
{

	public static void main(final String[] args) throws NotFound
	{
		new ExampleDumpSteamsideXmlUncollected().run();
	}

	void run()
	{
		Stream< ? extends Tag> apps = new Uncollected(steamClientHome, tags).apps();
		Stream<AppId> appids = apps.map(Tag::appid);
		Stream<String> lines =
			appids.map(appid -> steamClientHome.apps().find(appid).toString());

		new Out("tagless", lines, System.out::println).out();
	}

	SteamClientHome steamClientHome = Libraries.fromSteamPhysicalFiles();

	InMemoryTagsHome tags = SteamsideData_ForExamples.fromXmlFile();
}
