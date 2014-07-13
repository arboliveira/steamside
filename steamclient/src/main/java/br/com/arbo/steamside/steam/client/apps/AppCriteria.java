package br.com.arbo.steamside.steam.client.apps;

import java.util.stream.Stream;

public class AppCriteria {

	public static boolean isAll(AppCriteria c)
	{
		if (c == null) return true;
		if (c.gamesOnly) return false;
		if (c.currentPlatformOnly) return false;
		return true;
	}

	public Stream<App> filter(Stream<App> stream)
	{
		Stream<App> s = stream;
		if (this.gamesOnly)
			s = s.filter(App::isGame);
		if (this.currentPlatformOnly)
			s = s.filter(new FilterPlatform());
		return s;
	}

	public boolean gamesOnly;

	public boolean currentPlatformOnly;
}
