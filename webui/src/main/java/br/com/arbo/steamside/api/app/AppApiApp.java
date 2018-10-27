package br.com.arbo.steamside.api.app;

import br.com.arbo.steamside.steam.client.apps.App;
import br.com.arbo.steamside.steam.client.apps.NeverPlayed;
import br.com.arbo.steamside.steam.client.localfiles.appinfo.NotAvailableOnThisPlatform;
import br.com.arbo.steamside.steam.client.types.AppId;

public class AppApiApp implements AppApi
{

	@Override
	public AppId appid()
	{
		return app.appid();
	}

	@Override
	public String lastPlayedOrCry() throws NeverPlayed
	{
		return app.lastPlayedOrCry();
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

	public AppApiApp(App app)
	{
		this.app = app;
	}

	private final App app;

}
