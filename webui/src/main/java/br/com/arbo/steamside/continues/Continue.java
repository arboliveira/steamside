package br.com.arbo.steamside.continues;

import br.com.arbo.steamside.collection.CollectionFromVdf;
import br.com.arbo.steamside.types.Category;

public class Continue {

	public interface Needs {

		Category name();

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
