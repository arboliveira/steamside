package br.com.arbo.steamside.settings.file;

import br.com.arbo.steamside.collections.CollectionI;
import br.com.arbo.steamside.collections.CollectionsHome;
import br.com.arbo.steamside.data.collections.NotFound;
import br.com.arbo.steamside.steam.client.localfiles.SteamDirectory_ForExamples;
import br.com.arbo.steamside.steam.client.localfiles.sharedconfig.Dir_userdata;
import br.com.arbo.steamside.steam.client.localfiles.sharedconfig.Dir_userid;
import br.com.arbo.steamside.types.CollectionName;
import br.com.arbo.steamside.xml.SteamsideXml;

public class ExampleLoad {

	public static void main(final String[] args) throws NotFound {
		final SteamsideXml xml = new Load(new File_steamside_xml(
				new Dir_userid(new Dir_userdata(
						SteamDirectory_ForExamples.fromSteamPhysicalFiles()))))
				.load();
		final CollectionsHome home =
				xml.collections.toCollectionsHome();

		CollectionI collection =
				home.find(new CollectionName("Arbo"));
		collection.apps().forEach(System.out::println);
	}
}
