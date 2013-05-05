package br.com.arbo.steamside.collection;

import br.com.arbo.steamside.apps.App;
import br.com.arbo.steamside.kids.KidsMode;
import br.com.arbo.steamside.types.Category;

public class FilterKidsMode implements Filter {

	private final KidsMode kidsmode;

	public FilterKidsMode(final KidsMode kidsmode) {
		this.kidsmode = kidsmode;
	}

	@Override
	public void consider(final App app) throws Reject {
		if (kidsmode.isKidsModeOn() && kidsMustNotSeeThisCategory(app))
			throw new Reject();
	}

	private boolean kidsMustNotSeeThisCategory(final App app) {
		return !app.isInCategory(
				new Category(kidsmode.getCategoryAllowedToBeSeen()));
	}

}