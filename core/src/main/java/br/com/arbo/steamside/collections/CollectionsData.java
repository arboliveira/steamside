package br.com.arbo.steamside.collections;

import java.util.stream.Stream;

import br.com.arbo.steamside.data.collections.NotFound;
import br.com.arbo.steamside.types.AppId;
import br.com.arbo.steamside.types.CollectionName;

public interface CollectionsData
		extends CollectionsWrites, CollectionsQueries {

	default CollectionI addIfAbsent(CollectionName collection)
	{
		try {
			return this.find(collection);
		}
		catch (NotFound e) {
			CollectionImpl in =
					new CollectionImpl(collection, CollectionI.IsSystem.NO);
			this.add(in);
			return in;
		}
	}

	default Stream< ? extends Tag> appsOf(CollectionName collectionName)
	{
		CollectionI find = this.find(collectionName);
		return this.apps(find);
	}

	default void tag(CollectionName collection, AppId appid)
	{
		CollectionI find = this.addIfAbsent(collection);
		this.tag(find, appid);
	}

}
