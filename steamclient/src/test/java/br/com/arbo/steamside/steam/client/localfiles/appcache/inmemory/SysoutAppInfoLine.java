package br.com.arbo.steamside.steam.client.localfiles.appcache.inmemory;

import br.com.arbo.steamside.steam.client.apps.MissingFrom_appinfo_vdf;
import br.com.arbo.steamside.steam.client.localfiles.appcache.entry.AppInfo;
import br.com.arbo.steamside.steam.client.types.AppId;

public class SysoutAppInfoLine {

	public SysoutAppInfoLine(final Data_appinfo_vdf appinfo)
	{
		this.appinfo = appinfo;
	}

	public String toInfo(final AppId appid)
	{
		return appid + "]" + appname(appid);
	}

	private String appname(final AppId appid)
	{
		try
		{
			final AppInfo app = appinfo.get(appid);
			return app.appnametype();
		}
		catch (final MissingFrom_appinfo_vdf e)
		{
			return ">>>>" + e.getMessage() + "<<<<";
		}
	}

	private final Data_appinfo_vdf appinfo;

}
