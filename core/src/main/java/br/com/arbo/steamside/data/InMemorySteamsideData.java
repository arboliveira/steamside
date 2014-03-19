package br.com.arbo.steamside.data;

import javax.inject.Inject;

import br.com.arbo.steamside.collections.CollectionsData;
import br.com.arbo.steamside.collections.InMemoryCollectionsHome;

public class InMemorySteamsideData implements SteamsideData {

	@Inject
	public InMemorySteamsideData(InMemoryCollectionsHome c) {
		this.collections = c;
	}

	@Override
	public CollectionsData collections() {
		return collections;
	}

	private final InMemoryCollectionsHome collections;

}
