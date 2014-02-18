package br.com.arbo.steamside.xml.collections;

import java.util.LinkedList;

import org.eclipse.jdt.annotation.NonNull;

import br.com.arbo.steamside.data.collections.NotFound;
import br.com.arbo.steamside.data.collections.OnCollection;
import br.com.arbo.steamside.types.AppId;
import br.com.arbo.steamside.types.CollectionName;

public class CollectionsXml {

	public final LinkedList<CollectionXml> collection = new LinkedList<CollectionXml>();

	public void create(final CollectionName name) {
		try {
			find(name);
		} catch (final NotFound e) {
			final CollectionXml anew = new CollectionXml();
			anew.name = name.value;
			collection.add(anew);
		}
	}

	public void add(
			@NonNull final CollectionName name,
			@NonNull final AppId appid) throws NotFound {
		final CollectionXml collectionXml = find(name);
		collectionXml.add(appid);
	}

	public OnCollection on(final CollectionName name) throws NotFound {
		return find(name);
	}

	private CollectionXml find(final CollectionName name) throws NotFound {
		for (final CollectionXml collectionXml : collection)
			if (name.value.equals(collectionXml.name))
				return collectionXml;
		throw new NotFound();
	}

}
