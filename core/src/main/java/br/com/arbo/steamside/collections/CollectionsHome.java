package br.com.arbo.steamside.collections;

import java.util.stream.Stream;

import br.com.arbo.steamside.data.collections.NotFound;
import br.com.arbo.steamside.types.CollectionName;

public interface CollectionsHome {

	Stream< ? extends CollectionOfApps> all();

	CollectionOfApps find(CollectionName name) throws NotFound;
}
