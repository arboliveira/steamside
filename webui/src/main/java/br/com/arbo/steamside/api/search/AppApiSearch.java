package br.com.arbo.steamside.api.search;

import org.eclipse.jdt.annotation.NonNull;

import br.com.arbo.steamside.api.app.AppApi;
import br.com.arbo.steamside.steam.client.apps.NeverPlayed;
import br.com.arbo.steamside.steam.client.types.AppId;
import br.com.arbo.steamside.steam.store.App;

public class AppApiSearch implements AppApi {

	public AppApiSearch(App app)
	{
		this.app = app;
	}

	@Override
	public AppId appid()
	{
		return app.appid;
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
		return app.name.name;
	}

	@Override
	public boolean unavailable()
	{
		return false;
	}

	private final App app;

}
