package br.com.arbo.steamside.collections;

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
			CollectionImpl in = new CollectionImpl(collection);
			this.add(in);
			return in;
		}
	}

	default void tag(CollectionName collection, AppId appid) throws NotFound
	{
		CollectionI find = this.addIfAbsent(collection);
		this.tag(find, appid);
	}

}
