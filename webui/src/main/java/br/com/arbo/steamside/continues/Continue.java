package br.com.arbo.steamside.continues;

import java.util.List;

import br.com.arbo.steamside.collection.CollectionFromVdf;
import br.com.arbo.steamside.types.Category;
import br.com.arbo.steamside.webui.appdto.AppDTO;

public class Continue {

	public interface Needs {

		Category name();

		CollectionFromVdf collectionFromVdf();
	}

	private final Needs needs;

	public Continue(final Needs needs) {
		this.needs = needs;
	}

	public List<AppDTO> fetch() {

		return needs.collectionFromVdf().fetchAll().apps;
	}
}
