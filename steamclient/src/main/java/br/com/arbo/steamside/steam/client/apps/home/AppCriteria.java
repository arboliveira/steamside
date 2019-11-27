package br.com.arbo.steamside.steam.client.apps.home;

import java.util.Optional;

import br.com.arbo.steamside.steam.client.apps.Platform;

public class AppCriteria
{

	public static final AppCriteria OWNED =
		new AppCriteria().gamesOnly(false).owned(true);

	public static boolean isAll(AppCriteria c)
	{
		if (c.gamesOnly) return false;
		if (c.owned) return false;
		if (c.platform != null) return false;
		return true;
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

	public boolean lastPlayedDescending()
	{
		return lastPlayedDescending;
	}

	public AppCriteria lastPlayedDescending(boolean b)
	{
		lastPlayedDescending = b;
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

	public AppCriteria platform(Optional<Platform> platform)
	{
		platform.ifPresent(this::platform);
		return this;
	}

	public AppCriteria platform(Platform platform)
	{
		this.platform = platform;
		return this;
	}

	public Platform platform()
	{
		return platform;
	}

	private boolean gamesOnly;

	private boolean lastPlayedDescending;
	private boolean owned;
	private Platform platform;
}
