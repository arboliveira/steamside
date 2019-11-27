package br.com.arbo.steamside.steam.store;

import br.com.arbo.steamside.steam.client.types.AppId;
import br.com.arbo.steamside.steam.client.types.AppName;

public class App
{

	public App(AppId appid, AppName name)
	{
		this.appid = appid;
		this.name = name;
	}

	@Override
	public String toString()
	{
		return appid.toString() + ":" + name.toString();
	}

	public final AppId appid;

	public final AppName name;

}
