package br.com.arbo.steamside.api.search;

import br.com.arbo.steamside.api.app.AppApi;
import br.com.arbo.steamside.steam.store.App;

public class AppApiSearch implements AppApi {

	public AppApiSearch(App app)
	{
		this.app = app;
	}

	@Override
	public String appid()
	{
		return app.appid.appid;
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
