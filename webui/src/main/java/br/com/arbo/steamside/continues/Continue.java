package br.com.arbo.steamside.continues;

import br.com.arbo.steamside.collection.CollectionFromVdf;

public class Continue {

	public interface Needs {

		String name();

		CollectionFromVdf collectionFromVdf();
	}

	private final Needs needs;

	public Continue(final Needs needs) {
		this.needs = needs;
	}

	public Object fetch() {

		return needs.collectionFromVdf().fetch(needs.name()).apps;
	}
}
