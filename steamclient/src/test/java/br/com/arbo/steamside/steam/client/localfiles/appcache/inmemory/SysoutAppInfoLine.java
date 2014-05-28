package br.com.arbo.steamside.steam.client.localfiles.appcache.inmemory;

import br.com.arbo.steamside.steam.client.apps.MissingFrom_appinfo_vdf;
import br.com.arbo.steamside.steam.client.types.AppNameType;

public class SysoutAppInfoLine {

	public static String toInfo(AppNameType appnametype)
	{
		return appnametype.appid() + "]" + appnametype(appnametype);
	}

	private static String appnametype(AppNameType appnametype)
	{
		try
		{
			return appnametype.appnametype();
		}
		catch (final MissingFrom_appinfo_vdf e)
		{
			return ">>>>" + e.getMessage() + "<<<<";
		}
	}

}
