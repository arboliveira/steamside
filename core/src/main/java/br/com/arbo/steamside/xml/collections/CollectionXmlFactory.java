package br.com.arbo.steamside.xml.collections;

import br.com.arbo.steamside.collections.CollectionI;
import br.com.arbo.steamside.collections.CollectionsQueries;

public class CollectionXmlFactory {

	public static CollectionXml valueOf(
			CollectionI collection, CollectionsQueries home)
	{
		CollectionXml xml = new CollectionXml();
		xml.name = collection.name().value;
		home.apps(collection).forEach(c -> xml.tags.add(c));
		return xml;
	}

}
