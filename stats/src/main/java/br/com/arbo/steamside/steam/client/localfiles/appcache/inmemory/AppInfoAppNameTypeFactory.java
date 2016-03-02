package br.com.arbo.steamside.steam.client.localfiles.appcache.inmemory;

import br.com.arbo.steamside.steam.client.types.AppId;
import br.com.arbo.steamside.steam.client.types.AppNameType;

public class AppInfoAppNameTypeFactory
{

	public AppNameType produce(AppId appid)
	{
		return new AppInfoAppNameType(appid, appinfoFactory);
	}

	public AppInfoAppNameTypeFactory(Data_appinfo_vdf appinfoFactory)
	{
		this.appinfoFactory = appinfoFactory;
	}

	private final Data_appinfo_vdf appinfoFactory;
}
