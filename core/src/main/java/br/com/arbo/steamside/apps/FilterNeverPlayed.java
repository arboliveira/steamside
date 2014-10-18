package br.com.arbo.steamside.apps;

import java.util.function.Predicate;

import br.com.arbo.steamside.steam.client.apps.App;
import br.com.arbo.steamside.steam.client.apps.NeverPlayed;

public class FilterNeverPlayed implements Predicate<App> {

	@Override
	public boolean test(final App app)
	{
		try
		{
			app.lastPlayedOrCry();
		}
		catch (final NeverPlayed ex)
		{
			return false;
		}
		return true;
	}

}
