package br.com.arbo.steamside.continues;

import java.util.function.Predicate;

import br.com.arbo.steamside.apps.FilterKidsMode;
import br.com.arbo.steamside.apps.FilterNeverPlayed;
import br.com.arbo.steamside.kids.KidsModeDetector;
import br.com.arbo.steamside.steam.client.apps.App;

class FilterContinues implements Predicate<App>
{

	@SafeVarargs
	private static boolean considerAll(
		final App app, final Predicate<App>... filters)
	{
		for (final Predicate<App> filter : filters)
			if (!filter.test(app)) return false;
		return true;
	}

	@Override
	public boolean test(final App app)
	{
		return considerAll(app,
			new FilterNeverPlayed(),
			new FilterKidsMode(kidsModeDetector));
	}

	FilterContinues(final KidsModeDetector kidsModeDetector)
	{
		this.kidsModeDetector = kidsModeDetector;
	}

	private final KidsModeDetector kidsModeDetector;

}
