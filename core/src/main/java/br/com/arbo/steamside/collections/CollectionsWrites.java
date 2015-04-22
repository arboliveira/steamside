package br.com.arbo.steamside.collections;

import br.com.arbo.steamside.data.collections.Duplicate;

public interface CollectionsWrites {

	void add(CollectionI in) throws Duplicate;

	void delete(CollectionI in);

	void favorite(CollectionI in);

}
