package br.com.arbo.steamside.apps;

import br.com.arbo.steamside.types.Category;

public class FilterCategory implements Filter {

	private final Category category;

	public FilterCategory(final Category category) {
		this.category = category;
	}

	@Override
	public void consider(final App app) throws Reject {
		if (!app.isInCategory(category)) throw new Reject();
	}

}