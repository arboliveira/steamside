package br.com.arbo.steamside.collections;

import br.com.arbo.steamside.data.collections.NotFound;
import br.com.arbo.steamside.types.AppId;
import br.com.arbo.steamside.types.CollectionName;

public interface CollectionsData
		extends CollectionsWrites, CollectionsQueries {

	default void tag(CollectionName collection, AppId appid) throws NotFound
	{
		this.tag(this.find(collection), appid);
	}

}
