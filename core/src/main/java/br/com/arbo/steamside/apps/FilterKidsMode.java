package br.com.arbo.steamside.apps;

import java.util.function.Predicate;

import br.com.arbo.steamside.kids.KidsMode;
import br.com.arbo.steamside.steam.client.apps.App;
import br.com.arbo.steamside.steam.client.types.SteamCategory;

public class FilterKidsMode implements Predicate<App> {

	public FilterKidsMode(final KidsMode kidsmode)
	{
		this.kidsmode = kidsmode;
	}

	@Override
	public boolean test(final App app)
	{
		if (!kidsmode.isKidsModeOn()) return true;
		return kidsCanSeeThisCollection(app);
	}

	private boolean kidsCanSeeThisCollection(final App app)
	{
		String name = kidsmode.getCollection().value;
		return app.isInCategory(new SteamCategory(name));
	}

	private final KidsMode kidsmode;

}