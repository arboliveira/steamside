package br.com.arbo.steamside.xml.collections;

import br.com.arbo.steamside.collections.TagsQueries;

public class CollectionsXmlFactory {

	public static CollectionsXml valueOf(TagsQueries tags)
	{
		CollectionXmlFactory xmlFactory = new CollectionXmlFactory(tags);

		CollectionsXml xml = new CollectionsXml();

		tags.collections().all()
				.map(xmlFactory::valueOf)
				.forEach(xml.collection::add);

		return xml;
	}

}
