package br.com.arbo.steamside.xml.collections;

import br.com.arbo.steamside.collections.CollectionI;
import br.com.arbo.steamside.collections.CollectionImpl;
import br.com.arbo.steamside.types.CollectionName;

public class CollectionXml {

	public static CollectionXml valueOf(CollectionI collection) {
		CollectionXml xml = new CollectionXml();
		xml.name = collection.name().value;
		collection.apps().forEach(c -> xml.tags.add(c));
		return xml;
	}

	public CollectionImpl toCollection() {
		final CollectionName name = new CollectionName(this.name);
		return new CollectionImpl(name);
	}

	public String name;

	public final TagsXml tags = new TagsXml();

}
