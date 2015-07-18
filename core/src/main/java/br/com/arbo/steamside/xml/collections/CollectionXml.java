package br.com.arbo.steamside.xml.collections;

import br.com.arbo.steamside.collections.CollectionImpl;
import br.com.arbo.steamside.types.CollectionName;

public class CollectionXml
{

	public CollectionXml()
	{
		//
	}

	public CollectionImpl toCollection()
	{
		CollectionName name = new CollectionName(this.name);
		return new CollectionImpl(name);
	}

	public String name;

	public TagsXml tags = new TagsXml();

}
