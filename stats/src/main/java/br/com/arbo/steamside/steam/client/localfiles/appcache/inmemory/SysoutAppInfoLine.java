package br.com.arbo.steamside.steam.client.localfiles.appcache.inmemory;

import java.util.Comparator;
import java.util.stream.Stream;

import br.com.arbo.steamside.steam.client.localfiles.appinfo.Data_appinfo_vdf;
import br.com.arbo.steamside.steam.client.types.AppId;
import br.com.arbo.steamside.steam.client.types.AppNameType;

public class SysoutAppInfoLine
{

	public static Stream<String> lines(
		Stream<AppId> appids, Data_appinfo_vdf appinfoFactory)
	{
		AppInfoAppNameTypeFactory a =
			new AppInfoAppNameTypeFactory(appinfoFactory);
		Stream<AppNameType> t = appids.map(a::produce);
		Stream<AppNameType> sorted =
			t.sorted(Comparator.comparing(AppNameType::appnametype_or_missing));
		Stream<String> lines = sorted.map(SysoutAppInfoLine::toInfo);
		return lines;
	}

	public static String toInfo(AppNameType appnametype)
	{
		return appnametype.appid() + "]" + appnametype.appnametype_or_missing();
	}

}
