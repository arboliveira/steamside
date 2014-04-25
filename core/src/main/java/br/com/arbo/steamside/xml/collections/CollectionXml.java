package br.com.arbo.steamside.xml.collections;

import org.eclipse.jdt.annotation.Nullable;

import br.com.arbo.steamside.collections.CollectionI;
import br.com.arbo.steamside.collections.CollectionImpl;
import br.com.arbo.steamside.types.CollectionName;

public class CollectionXml {

	public static CollectionXml valueOf(CollectionI collection)
	{
		CollectionXml xml = new CollectionXml();
		xml.name = collection.name().value;
		xml.system = collection.isSystem().toDTOString();
		collection.apps().forEach(c -> xml.tags.add(c));
		return xml;
	}

	public CollectionImpl toCollection()
	{
		final CollectionName name = new CollectionName(this.name);
		return new CollectionImpl(name,
				CollectionI.IsSystem.fromDTOString(system));
	}

	public String name;

	@Nullable
	public String system;

	public final TagsXml tags = new TagsXml();

}
