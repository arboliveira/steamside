package br.com.arbo.steamside.collections.system;

import java.util.stream.Stream;

import br.com.arbo.steamside.apps.AppCriteria;
import br.com.arbo.steamside.collections.CollectionI.IsSystem;
import br.com.arbo.steamside.collections.CollectionImpl;
import br.com.arbo.steamside.collections.CollectionsData;
import br.com.arbo.steamside.collections.Tag;
import br.com.arbo.steamside.collections.TagImpl;
import br.com.arbo.steamside.data.collections.NotFound;
import br.com.arbo.steamside.library.Library;
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

	Stream< ? extends Tag> appsOf(
			CollectionName collectionName, AppCriteria criteria)
			{
		if (!instance.name().equalsCollectionName(collectionName))
			throw new NotFound();

		return library.allApps(criteria).map(app -> app.appid())
				.filter(appid -> !collections.isCollected(appid))
				.map(TagImpl::new);
			}

	final CollectionImpl instance;

	private final CollectionsData collections;

	private final Library library;

}
