package br.com.arbo.steamside.collections;

import java.util.stream.Stream;

import br.com.arbo.steamside.data.collections.NotFound;
import br.com.arbo.steamside.types.CollectionName;

public interface CollectionsQueries {

	Stream< ? extends CollectionI> all();

	CollectionI favorite() throws FavoriteNotSet;

	CollectionI find(CollectionName name) throws NotFound;

}
