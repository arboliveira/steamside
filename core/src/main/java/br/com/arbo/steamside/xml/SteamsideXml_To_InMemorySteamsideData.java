package br.com.arbo.steamside.xml;

import br.com.arbo.steamside.data.InMemorySteamsideData;

public class SteamsideXml_To_InMemorySteamsideData
{

	public static InMemorySteamsideData toSteamsideData(SteamsideXml xml)
	{
		InMemorySteamsideData d = InMemorySteamsideData.newInstance();
		xml.collections.toCollectionsHome(d.collections(), d.tags());
		xml.recentTags.toTagsHome(d.tags());
		xml.kids.toKidsHome(d.kids());
		xml.favorites.toCollectionsHome(d.collections());
		return d;
	}

}