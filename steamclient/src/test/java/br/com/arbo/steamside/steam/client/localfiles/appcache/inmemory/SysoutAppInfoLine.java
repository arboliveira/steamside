package br.com.arbo.steamside.steam.client.localfiles.appcache.inmemory;

import java.util.function.Consumer;
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

	public SysoutAppInfoLine(Data_appinfo_vdf appnameFactory)
	{
		this.appnameFactory = appnameFactory;
	}

	public void forEach(Stream<AppId> appids, Consumer<String> action)
	{
		appids.map(this::toInfo).parallel().forEach(action);
	}

	private String toInfo(AppId appid)
	{
		return SysoutAppInfoLine.toInfo(
			new AppInfoAppNameType(appid, appnameFactory));
	}

	private final Data_appinfo_vdf appnameFactory;

}
