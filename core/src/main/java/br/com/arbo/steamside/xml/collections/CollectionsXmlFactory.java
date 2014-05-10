package br.com.arbo.steamside.xml.collections;

import br.com.arbo.steamside.collections.CollectionsQueries;

public class CollectionsXmlFactory {

	public static CollectionsXml valueOf(CollectionsQueries collections)
	{
		CollectionsXml xml = new CollectionsXml();

		collections.all()
				.map(each -> CollectionXmlFactory.valueOf(each, collections))
				.forEach(xml.collection::add);

		return xml;
	}

}
