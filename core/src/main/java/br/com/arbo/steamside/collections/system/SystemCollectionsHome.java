package br.com.arbo.steamside.collections.system;

import java.util.stream.Stream;

import br.com.arbo.steamside.collections.CollectionI;
import br.com.arbo.steamside.collections.CollectionsData;
import br.com.arbo.steamside.collections.Tag;
import br.com.arbo.steamside.data.collections.NotFound;
import br.com.arbo.steamside.library.Library;
import br.com.arbo.steamside.types.CollectionName;

public class SystemCollectionsHome {

	public SystemCollectionsHome(Library library, CollectionsData collections) {
		this.collections = collections;
		this.uncollected = new Uncollected(library, collections);
		this.everything = new Everything(library);
	}

	public Stream< ? extends CollectionI> all()
	{
		return Stream.concat(
				collections.all(),
				Stream.of(uncollected.instance, everything.instance));
	}

	public Stream< ? extends Tag> appsOf(CollectionName collectionName)
	{
		try {
			return uncollected.appsOf(collectionName);
		}
		catch (NotFound nf1) {
			try {
				return everything.appsOf(collectionName);
			}
			catch (NotFound nf2) {
				return collections.appsOf(collectionName);
			}
		}
	}

	private final CollectionsData collections;

	private final Everything everything;

	private final Uncollected uncollected;
}
