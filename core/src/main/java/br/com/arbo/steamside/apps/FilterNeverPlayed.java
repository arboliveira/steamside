package br.com.arbo.steamside.apps;

import java.util.function.Predicate;

import br.com.arbo.steamside.steam.client.apps.App;

public class FilterNeverPlayed implements Predicate<App>
{

	@Override
	public boolean test(final App app)
	{
		return app.lastPlayed().isPresent();
	}

}
