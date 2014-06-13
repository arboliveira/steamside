package br.com.arbo.steamside.collections;

import br.com.arbo.steamside.steam.client.types.AppId;
import br.com.arbo.steamside.types.CollectionName;

public interface TagsData extends TagsQueries, TagsWrites {

	@Override
	CollectionsData collections();

	@Deprecated
	default void tag(CollectionName collection, AppId appid)
	{
		CollectionI find = collections().addIfAbsent(collection);
		this.tag(find, appid);
	}

	default void tagRemember(CollectionName collection, AppId appid)
	{
		CollectionI find = collections().addIfAbsent(collection);
		this.tagRemember(find, appid);
	}

}
