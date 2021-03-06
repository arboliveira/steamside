package br.com.arbo.steamside.steam.client.apps;

import br.com.arbo.steamside.steam.client.types.AppId;
import br.com.arbo.steamside.steam.client.types.AppNameType;
import br.com.arbo.steamside.steam.client.types.AppNameTypes;

public class AppAppNameType implements AppNameType
{

	@Override
	public AppId appid()
	{
		return app.appid();
	}

	@Override
	public String appnametype() throws MissingFrom_appinfo_vdf
	{
		return AppNameTypes.appnametype(app.name(), app.type());
	}

	public AppAppNameType(App app)
	{
		this.app = app;
	}

	private final App app;
}
