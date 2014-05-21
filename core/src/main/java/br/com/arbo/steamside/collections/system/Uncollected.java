package br.com.arbo.steamside.collections.system;

import java.util.stream.Stream;

import br.com.arbo.steamside.apps.AppCriteria;
import br.com.arbo.steamside.collections.CollectionI;
import br.com.arbo.steamside.collections.CollectionI.IsSystem;
import br.com.arbo.steamside.collections.CollectionImpl;
import br.com.arbo.steamside.collections.CollectionsData;
import br.com.arbo.steamside.collections.CollectionsQueries.WithCount;
import br.com.arbo.steamside.collections.Tag;
import br.com.arbo.steamside.collections.TagImpl;
import br.com.arbo.steamside.data.collections.NotFound;
import br.com.arbo.steamside.library.Library;
import br.com.arbo.steamside.types.AppId;
import br.com.arbo.steamside.types.CollectionName;

class Uncollected {

	public Uncollected(Library library, CollectionsData collections)
	{
		this.library = library;
		this.collections = collections;
		this.instance =
				new CollectionImpl(new CollectionName("(uncollected)"),
						IsSystem.YES);
	}

	public WithCount withCount()
	{
		return new WithCount() {

			@Override
			public CollectionI collection()
			{
				return instance;
			}

			@Override
			public int count()
			{
				return (int) uncollectedIds(null).count();
			}
		};
	}

	Stream< ? extends Tag> appsOf(
			CollectionName collectionName, AppCriteria criteria)
			{
		if (!instance.name().equalsCollectionName(collectionName))
			throw new NotFound();

		return uncollectedIds(criteria).map(TagImpl::new);
			}

	Stream<AppId> uncollectedIds(AppCriteria criteria)
	{
		return library.allApps(criteria).map(app -> app.appid())
				.filter(appid -> !collections.isCollected(appid));
	}

	final CollectionImpl instance;

	private final CollectionsData collections;

	private final Library library;

}
