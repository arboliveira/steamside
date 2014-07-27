package br.com.arbo.steamside.api.app;

import br.com.arbo.steamside.steam.client.apps.App;
import br.com.arbo.steamside.steam.client.localfiles.appcache.entry.NotAvailableOnThisPlatform;

public class AppApiApp implements AppApi {

	public AppApiApp(App app)
	{
		this.app = app;
	}

	@Override
	public String appid()
	{
		return app.appid().appid;
	}

	@Override
	public String name()
	{
		return app.name().name;
	}

	@Override
	public boolean unavailable()
	{
		try
		{
			app.executable();
			return false;
		}
		catch (NotAvailableOnThisPlatform not)
		{
			return true;
		}
	}

	private final App app;

}
