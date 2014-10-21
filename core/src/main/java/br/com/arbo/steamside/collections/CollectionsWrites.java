package br.com.arbo.steamside.collections;

import org.eclipse.jdt.annotation.NonNull;

import br.com.arbo.steamside.data.collections.Duplicate;

public interface CollectionsWrites {

	void add(@NonNull CollectionI in) throws Duplicate;

	void delete(@NonNull CollectionI in);

	void favorite(@NonNull CollectionI in);

}
