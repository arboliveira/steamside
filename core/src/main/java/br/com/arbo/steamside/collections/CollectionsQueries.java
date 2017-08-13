package br.com.arbo.steamside.collections;

import java.util.Optional;
import java.util.stream.Stream;

import br.com.arbo.steamside.data.collections.NotFound;
import br.com.arbo.steamside.types.CollectionName;

public interface CollectionsQueries
{

	Stream< ? extends CollectionI> all();

	Optional< ? extends CollectionI> favorite();

	CollectionI find(CollectionName name) throws NotFound;

}
