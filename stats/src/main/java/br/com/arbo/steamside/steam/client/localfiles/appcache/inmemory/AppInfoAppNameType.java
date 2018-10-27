package br.com.arbo.steamside.steam.client.localfiles.appcache.inmemory;

import br.com.arbo.steamside.steam.client.apps.MissingFrom_appinfo_vdf;
import br.com.arbo.steamside.steam.client.localfiles.appinfo.AppInfo;
import br.com.arbo.steamside.steam.client.localfiles.appinfo.Data_appinfo_vdf;
import br.com.arbo.steamside.steam.client.types.AppId;
import br.com.arbo.steamside.steam.client.types.AppNameType;
import br.com.arbo.steamside.steam.client.types.AppNameTypes;

public class AppInfoAppNameType implements AppNameType
{

	@Override
	public AppId appid()
	{
		return appid;
	}

	@Override
	public String appnametype() throws MissingFrom_appinfo_vdf
	{
		final AppInfo app = d_appinfo.get(appid);
		return AppNameTypes.appnametype(app.name(), app.type());
	}

	public AppInfoAppNameType(final AppId appid, final Data_appinfo_vdf appinfo)
	{
		this.appid = appid;
		this.d_appinfo = appinfo;
	}

	private final AppId appid;

	private final Data_appinfo_vdf d_appinfo;

}
