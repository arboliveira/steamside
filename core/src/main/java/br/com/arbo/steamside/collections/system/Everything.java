package br.com.arbo.steamside.collections.system;

import java.util.stream.Stream;

import br.com.arbo.steamside.collections.CollectionI.IsSystem;
import br.com.arbo.steamside.collections.CollectionImpl;
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

	public Stream< ? extends Tag> appsOf(CollectionName collectionName)
	{
		if (!instance.name().equalsCollectionName(collectionName))
			throw new NotFound();

		return library.allApps().map(app -> app.appid())
				.map(TagImpl::new);
	}

	final CollectionImpl instance;

	private final Library library;
}
