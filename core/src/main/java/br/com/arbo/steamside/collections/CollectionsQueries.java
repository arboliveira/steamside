package br.com.arbo.steamside.collections;

import java.util.stream.Stream;

import org.eclipse.jdt.annotation.NonNull;

import br.com.arbo.steamside.data.collections.NotFound;
import br.com.arbo.steamside.steam.client.apps.AppCriteria;
import br.com.arbo.steamside.steam.client.types.AppId;
import br.com.arbo.steamside.types.CollectionName;

public interface CollectionsQueries {

	Stream< ? extends CollectionI> all();

	Stream< ? extends WithCount> allWithCount(AppCriteria criteria);

	Stream< ? extends Tag> apps(CollectionI collection);

	@NonNull
	CollectionI find(CollectionName name) throws NotFound;

	boolean isCollected(AppId appid);

	Stream< ? extends CollectionI> tags(AppId app);

	public static interface WithCount {

		CollectionI collection();

		int count();
	}

}
