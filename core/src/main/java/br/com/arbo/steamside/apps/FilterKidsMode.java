package br.com.arbo.steamside.apps;

import br.com.arbo.steamside.kids.KidsMode;
import br.com.arbo.steamside.types.Category;

public class FilterKidsMode implements Filter {

	public FilterKidsMode(final KidsMode kidsmode) {
		this.kidsmode = kidsmode;
	}

	@Override
	public void consider(final App app) throws Reject
	{
		if (kidsmode.isKidsModeOn() && kidsMustNotSeeThisCategory(app))
			throw new Reject();
	}

	private boolean kidsMustNotSeeThisCategory(final App app)
	{
		String name = kidsmode.getCollection().value;
		return !app.isInCategory(new Category(name));
	}

	private final KidsMode kidsmode;

}