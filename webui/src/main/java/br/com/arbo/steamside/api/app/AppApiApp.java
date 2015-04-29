package br.com.arbo.steamside.api.app;

import br.com.arbo.steamside.steam.client.apps.App;
import br.com.arbo.steamside.steam.client.apps.MissingFrom_appinfo_vdf;
import br.com.arbo.steamside.steam.client.apps.NeverPlayed;
import br.com.arbo.steamside.steam.client.localfiles.appcache.entry.NotAvailableOnThisPlatform;
import br.com.arbo.steamside.steam.client.types.AppId;

public class AppApiApp implements AppApi {

	public AppApiApp(App app)
	{
		this.app = app;
	}

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
	public String name() throws MissingFrom_appinfo_vdf
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
