package br.com.arbo.steamside.steam.client.localfiles.appcache.inmemory;

import java.util.Comparator;
import java.util.stream.Stream;

import br.com.arbo.steamside.steam.client.apps.MissingFrom_appinfo_vdf;
import br.com.arbo.steamside.steam.client.types.AppId;
import br.com.arbo.steamside.steam.client.types.AppNameType;

public class SysoutAppInfoLine
{

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

	public SysoutAppInfoLine(Stream<AppId> appids,
		Data_appinfo_vdf appnameFactory)
	{
		this.appids = appids;
		this.appnameFactory = appnameFactory;
	}

	public Stream<String> lines()
	{
		return appids
			.map(appid -> new AppInfoAppNameType(appid, appnameFactory))
			.sorted(Comparator.comparing(SysoutAppInfoLine::appnametype))
			.map(appnametype -> SysoutAppInfoLine.toInfo(appnametype));
	}

	private final Stream<AppId> appids;

	private final Data_appinfo_vdf appnameFactory;

}
