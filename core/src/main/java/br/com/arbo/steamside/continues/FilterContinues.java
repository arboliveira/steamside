package br.com.arbo.steamside.continues;

import java.util.function.Predicate;

import br.com.arbo.steamside.apps.FilterKidsMode;
import br.com.arbo.steamside.apps.FilterNeverPlayed;
import br.com.arbo.steamside.kids.KidsMode;
import br.com.arbo.steamside.steam.client.apps.App;

class FilterContinues implements Predicate<App> {

	@SafeVarargs
	private static boolean considerAll(
			final App app, final Predicate<App>... filters)
	{
		for (final Predicate<App> filter : filters)
			if (!filter.test(app)) return false;
		return true;
	}

	FilterContinues(final KidsMode kidsmode)
	{
		this.kidsmode = kidsmode;
	}

	@Override
	public boolean test(final App app)
	{
		return considerAll(app,
				new FilterNeverPlayed(),
				new FilterKidsMode(kidsmode));
	}

	private final KidsMode kidsmode;

}
