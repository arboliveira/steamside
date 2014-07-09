package br.com.arbo.steamside.settings.file;

import br.com.arbo.steamside.collections.InMemoryCollectionsHome;
import br.com.arbo.steamside.collections.InMemoryTagsHome;
import br.com.arbo.steamside.steam.client.localfiles.sharedconfig.Dir_userdata;
import br.com.arbo.steamside.steam.client.localfiles.sharedconfig.Dir_userid;
import br.com.arbo.steamside.steam.client.localfiles.steamlocation.SteamLocations;
import br.com.arbo.steamside.xml.SteamsideXml;

public class SteamsideData_ForExamples {

	public static InMemoryTagsHome fromXmlFile()
	{
		File_steamside_xml file_steamside_xml =
				new File_steamside_xml(
						new Dir_userid(
								new Dir_userdata(
										SteamLocations
												.fromSteamPhysicalFiles())));

		SteamsideXml xml = new LoadSteamsideXml(file_steamside_xml).load();
		InMemoryCollectionsHome collections = new InMemoryCollectionsHome();
		InMemoryTagsHome tags = new InMemoryTagsHome(collections);
		xml.collections.toCollectionsHome(collections, tags);
		return tags;
	}

}