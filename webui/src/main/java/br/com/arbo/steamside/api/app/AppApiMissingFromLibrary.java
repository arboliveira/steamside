package br.com.arbo.steamside.api.app;

import org.eclipse.jdt.annotation.NonNull;

import br.com.arbo.steamside.steam.client.apps.NeverPlayed;
import br.com.arbo.steamside.steam.client.types.AppId;

public class AppApiMissingFromLibrary implements AppApi {

	public AppApiMissingFromLibrary(AppId appid)
	{
		this.appid = appid;
	}

	@Override
	public AppId appid()
	{
		return appid;
	}

	@Override
	@NonNull
	public String lastPlayedOrCry() throws NeverPlayed
	{
		throw new NeverPlayed();
	}

	@Override
	public String name()
	{
		return appid + " [missing from library]";
	}

	@Override
	public boolean unavailable()
	{
		return false;
	}

	private final AppId appid;

}
