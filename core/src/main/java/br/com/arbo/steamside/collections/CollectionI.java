package br.com.arbo.steamside.collections;

import java.util.stream.Stream;

import br.com.arbo.steamside.types.CollectionName;

public interface CollectionI {

	CollectionName name();

	Stream<Tag> apps();

}