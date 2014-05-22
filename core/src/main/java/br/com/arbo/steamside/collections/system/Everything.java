package br.com.arbo.steamside.collections.system;

import java.util.stream.Stream;

import br.com.arbo.steamside.apps.AppCriteria;
import br.com.arbo.steamside.collections.CollectionI;
import br.com.arbo.steamside.collections.CollectionI.IsSystem;
import br.com.arbo.steamside.collections.CollectionImpl;
import br.com.arbo.steamside.collections.CollectionsQueries.WithCount;
import br.com.arbo.steamside.collections.Tag;
import br.com.arbo.steamside.collections.TagImpl;
import br.com.arbo.steamside.data.collections.NotFound;
import br.com.arbo.steamside.library.Library;
import br.com.arbo.steamside.types.CollectionName;

class Everything {

	public Everything(Library library)
	{
		this.library = library;
		this.instance =
				new CollectionImpl(new CollectionName("(everything)"),
						IsSystem.YES);
	}

	public Stream< ? extends Tag> appsOf(
			CollectionName collectionName, AppCriteria criteria)
			{
		if (!instance.name().equalsCollectionName(collectionName))
			throw new NotFound();

		return library.allApps(criteria).map(app -> app.appid())
				.map(TagImpl::new);
			}

	public WithCount withCount(AppCriteria criteria)
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
				return library.count(criteria);
			}

		};
	}

	final CollectionImpl instance;

	final Library library;
}
