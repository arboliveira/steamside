package br.com.arbo.steamside.apps;

import java.util.Comparator;

import br.com.arbo.steamside.steam.client.apps.LastPlayed;
import br.com.arbo.steamside.steam.client.apps.NeverPlayed;

public final class LastPlayedDescending
		implements Comparator<LastPlayed> {

	private static Long lastPlayedAsLong(LastPlayed a)
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
	public int compare(final LastPlayed a1, final LastPlayed a2)
	{
		return lastPlayedAsLong(a2).compareTo(lastPlayedAsLong(a1));
	}
}