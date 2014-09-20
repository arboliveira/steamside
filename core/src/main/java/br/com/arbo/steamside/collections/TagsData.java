package br.com.arbo.steamside.collections;

import java.util.LinkedList;
import java.util.stream.Stream;

import br.com.arbo.steamside.data.collections.NotFound;
import br.com.arbo.steamside.steam.client.types.AppId;
import br.com.arbo.steamside.types.CollectionName;

public interface TagsData extends TagsQueries, TagsWrites {

	@Override
	CollectionsData collections();

	default void delete(CollectionName collection)
	{
		CollectionI find = collections().find(collection);
		Stream< ? extends Tag> apps = this.apps(find);
		Stream<AppId> appids = apps.map(Tag::appid);
		LinkedList<AppId> noconcurrent = appids.collect(
				LinkedList::new, LinkedList::add,
				LinkedList::addAll);
		noconcurrent.forEach(appid -> this.untag(find, appid));
		collections().delete(find);
	}

	default void tagn(CollectionName collection, Stream<AppId> apps)
	{
		CollectionI find = collections().addIfAbsent(collection);
		this.tag(find, apps);
	}

	default void tagRemember(CollectionName collection, AppId appid)
	{
		CollectionI find = collections().addIfAbsent(collection);
		this.tagRemember(find, appid);
	}

	default void untag(CollectionName collection, AppId appid)
	{
		CollectionI find;

		try
		{
			find = collections().find(collection);
		}
		catch (NotFound e)
		{
			return;
		}

		this.untag(find, appid);
	}
}
