package br.com.arbo.steamside.settings.file;

import java.util.stream.Stream;

import br.com.arbo.steamside.collections.CollectionI;
import br.com.arbo.steamside.collections.InMemoryCollectionsHome;
import br.com.arbo.steamside.collections.Tag;
import br.com.arbo.steamside.collections.system.SystemCollectionsHome;
import br.com.arbo.steamside.data.collections.NotFound;
import br.com.arbo.steamside.library.Library;
import br.com.arbo.steamside.library.Library_ForExamples;
import br.com.arbo.steamside.steam.client.localfiles.SteamDirectory_ForExamples;
import br.com.arbo.steamside.steam.client.localfiles.sharedconfig.Dir_userdata;
import br.com.arbo.steamside.steam.client.localfiles.sharedconfig.Dir_userid;
import br.com.arbo.steamside.types.AppId;
import br.com.arbo.steamside.types.CollectionName;
import br.com.arbo.steamside.xml.SteamsideXml;

public class ExampleLoad {

	public static void main(final String[] args) throws NotFound
	{
		new ExampleLoad().run();
	}

	void printTag(Tag tag) throws br.com.arbo.steamside.apps.NotFound
	{
		AppId appid = tag.appid();

		System.out.println(library.find(appid));
	}

	void run()
	{
		File_steamside_xml file_steamside_xml =
				new File_steamside_xml(
						new Dir_userid(
								new Dir_userdata(
										SteamDirectory_ForExamples
										.fromSteamPhysicalFiles())));

		SteamsideXml xml = new LoadSteamsideXml(file_steamside_xml).load();
		InMemoryCollectionsHome home = xml.collections.toCollectionsHome();

		final Stream< ? extends Tag> apps;
		if (false) {
			CollectionI collection =
					home.find(new CollectionName("Art"));
			apps = home.apps(collection);
		}
		else {
			SystemCollectionsHome sys =
					new SystemCollectionsHome(library, home);
			apps = sys.appsOf(new CollectionName("(uncollected)"));
		}
		apps.forEach(this::printTag);
	}

	Library library = Library_ForExamples.fromSteamPhysicalFiles();
}
