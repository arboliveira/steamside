package br.com.arbo.steamside.collections;

import java.util.stream.Stream;

import br.com.arbo.steamside.collections.CollectionsQueries.WithCount;
import br.com.arbo.steamside.steam.client.apps.AppCriteria;
import br.com.arbo.steamside.steam.client.types.AppId;
import br.com.arbo.steamside.types.CollectionName;

public interface TagsQueries {

	Stream< ? extends WithCount> allWithCount(AppCriteria criteria);

	Stream< ? extends Tag> apps(CollectionI collection);

	default Stream< ? extends Tag> appsOf(CollectionName collectionName)
	{
		CollectionI find = collections().find(collectionName);
		return this.apps(find);
	}

	CollectionsQueries collections();

	boolean isCollected(AppId appid);

	Stream< ? extends CollectionI> tags(AppId app);

}
