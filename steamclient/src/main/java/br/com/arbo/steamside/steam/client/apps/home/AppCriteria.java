package br.com.arbo.steamside.steam.client.apps.home;

import java.util.stream.Stream;

import br.com.arbo.steamside.steam.client.types.AppId;

public class AppCriteria
{

	public static final AppCriteria OWNED =
		new AppCriteria().gamesOnly(false).owned(true);

	public static boolean isAll(AppCriteria c)
	{
		if (c.gamesOnly) return false;
		if (c.in != null) return false;
		if (c.owned) return false;
		if (c.currentPlatformOnly) return false;
		return true;
	}

	public boolean currentPlatformOnly()
	{
		return currentPlatformOnly;
	}

	public AppCriteria currentPlatformOnly(boolean currentPlatformOnly)
	{
		this.currentPlatformOnly = currentPlatformOnly;
		return this;
	}

	public boolean gamesOnly()
	{
		return gamesOnly;
	}

	public AppCriteria gamesOnly(boolean gamesOnly)
	{
		this.gamesOnly = gamesOnly;
		return this;
	}

	public Stream<AppId> in()
	{
		return in;
	}

	public AppCriteria in(Stream<AppId> in)
	{
		this.in = in;
		return this;
	}

	public boolean owned()
	{
		return owned;
	}

	public AppCriteria owned(boolean owned)
	{
		this.owned = owned;
		return this;
	}

	private boolean currentPlatformOnly;

	private boolean gamesOnly;

	private Stream<AppId> in;

	private boolean owned;
}
