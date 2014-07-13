package br.com.arbo.steamside.apps;

import java.util.Comparator;

import br.com.arbo.steamside.steam.client.apps.App;
import br.com.arbo.steamside.steam.client.apps.NeverPlayed;

public final class LastPlayedDescending implements Comparator<App> {

	private static Long lastPlayedAsLong(App a)
	{
		try
		{
			return Long.valueOf(a.lastPlayedOrCry());
		}
		catch (NeverPlayed e)
		{
			return Long.valueOf(0);
		}
	}

	@Override
	public int compare(final App a1, final App a2)
	{
		return lastPlayedAsLong(a2).compareTo(lastPlayedAsLong(a1));
	}
}