package br.com.arbo.steamside.xml.collections;

import br.com.arbo.steamside.collections.CollectionI;
import br.com.arbo.steamside.collections.TagsQueries;

public class CollectionXmlFactory {

	public CollectionXmlFactory(TagsQueries tags)
	{
		this.tags = tags;
	}

	public CollectionXml valueOf(
			CollectionI collection)
	{
		CollectionXml xml = new CollectionXml();
		xml.name = collection.name().value;
		tags.apps(collection).forEach(xml.tags::add);
		return xml;
	}

	private final TagsQueries tags;

}
