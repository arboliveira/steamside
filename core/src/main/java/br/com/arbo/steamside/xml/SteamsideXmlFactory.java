package br.com.arbo.steamside.xml;

import br.com.arbo.steamside.collections.TagsQueries;
import br.com.arbo.steamside.data.SteamsideData;
import br.com.arbo.steamside.xml.collections.CollectionsXmlFactory;
import br.com.arbo.steamside.xml.collections.FavoritesXmlFactory;
import br.com.arbo.steamside.xml.collections.RecentTagsXmlFactory;
import br.com.arbo.steamside.xml.kids.KidsXmlFactory;

public class SteamsideXmlFactory
{

	public static SteamsideXml valueOf(SteamsideData data)
	{
		TagsQueries tags = data.tags();
	
		return SteamsideXmlBuilder.builder()
			.collectionsXml(
				CollectionsXmlFactory.valueOf(tags))
			.favoritesXml(
				FavoritesXmlFactory.valueOf(data.collections()))
			.kidsXml(
				KidsXmlFactory.valueOf(data.kids()))
			.recentTagsXml(
				RecentTagsXmlFactory.valueOf(tags))
			.build();
	}

}
