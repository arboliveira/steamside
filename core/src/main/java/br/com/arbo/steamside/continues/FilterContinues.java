package br.com.arbo.steamside.continues;

import javax.inject.Inject;

import br.com.arbo.steamside.apps.App;
import br.com.arbo.steamside.apps.Filter;
import br.com.arbo.steamside.apps.FilterKidsMode;
import br.com.arbo.steamside.apps.FilterNeverPlayed;
import br.com.arbo.steamside.apps.FilterPlatform;
import br.com.arbo.steamside.kids.KidsMode;

public class FilterContinues implements Filter {

	@Override
	public void consider(final App app) throws Reject {
		considerAll(app,
				new FilterNeverPlayed(),
				new FilterKidsMode(kidsmode),
				new FilterPlatform());
	}

	private static void considerAll(final App app, final Filter... filters)
			throws Reject {
		for (final Filter filter : filters)
			filter.consider(app);
	}

	private final KidsMode kidsmode;

	@Inject
	public FilterContinues(final KidsMode kidsmode) {
		this.kidsmode = kidsmode;
	}

}
