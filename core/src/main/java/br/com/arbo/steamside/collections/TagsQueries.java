package br.com.arbo.steamside.collections;

import java.util.stream.Stream;

import br.com.arbo.steamside.steam.client.types.AppId;
import br.com.arbo.steamside.types.CollectionName;

public interface TagsQueries
{

	Stream< ? extends WithTags> allWithTags();

	Stream< ? extends Tag> apps(CollectionI collection);

	default Stream< ? extends Tag> appsOf(CollectionName collectionName)
	{
		CollectionI find = collections().find(collectionName);
		return this.apps(find);
	}

	CollectionsQueries collections();

	boolean isCollected(AppId appid);

	boolean isTagged(AppId appid, CollectionI collection);

	default boolean isTagged(AppId appid, CollectionName collectionName)
	{
		CollectionI find = collections().find(collectionName);
		return this.isTagged(appid, find);
	}

	Stream< ? extends WithCount> recent();

	Stream< ? extends CollectionI> tags(AppId app);

	public static interface WithCount
	{

		CollectionName collection();

		int count();
	}

	public static interface WithTags
	{

		CollectionI collection();

		Stream< ? extends Tag> tags();
	}

}
