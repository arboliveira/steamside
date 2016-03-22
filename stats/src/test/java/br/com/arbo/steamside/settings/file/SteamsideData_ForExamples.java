package br.com.arbo.steamside.settings.file;

import br.com.arbo.steamside.collections.InMemoryCollectionsHome;
import br.com.arbo.steamside.collections.InMemoryTagsHome;
import br.com.arbo.steamside.steam.client.localfiles.sharedconfig.Dirs_userid;
import br.com.arbo.steamside.xml.SteamsideXml;

public class SteamsideData_ForExamples
{

	public static InMemoryTagsHome fromXmlFile()
	{
		File_steamside_xml file_steamside_xml =
			new File_steamside_xml(
				Dirs_userid.fromSteamPhysicalFiles());

		SteamsideXml xml = load(file_steamside_xml);
		InMemoryCollectionsHome collections = new InMemoryCollectionsHome();
		InMemoryTagsHome tags = new InMemoryTagsHome(collections);
		xml.collections.toCollectionsHome(collections, tags);
		return tags;
	}

	private static SteamsideXml load(File_steamside_xml file_steamside_xml)
	{
		try
		{
			return new LoadSteamsideXml(file_steamside_xml).load();
		}
		catch (br.com.arbo.steamside.settings.file.LoadFile.Missing e)
		{
			throw new IllegalStateException(
				"Can't load "
					+ SteamsideXml.class.getSimpleName()
					+ " data. "
					+ "This machine has no steamside.xml file. "
					+ "Perhaps Steamside never ran here?",
				e);
		}
	}
}
