package br.com.arbo.steamside.steam.client.localfiles.appinfo;

import java.util.Comparator;
import java.util.Optional;
import java.util.stream.Stream;

import br.com.arbo.steamside.steam.client.types.AppId;
import br.com.arbo.steamside.steam.client.types.AppNameType;
import br.com.arbo.steamside.steam.client.types.AppNameTypes;

public class SysoutAppInfoLine
{

	public static Stream<String> lines(
		Stream<AppId> appids, Data_appinfo_vdf appinfoFactory)
	{
		Stream<AppNameType> t =
			appids.map(appid -> new AppInfoAppNameType(appid, appinfoFactory));
		Stream<AppNameType> sorted = t.sorted(
			Comparator.comparing(SysoutAppInfoLine::appnametype_or_missing));
		Stream<String> lines = sorted.map(SysoutAppInfoLine::toInfo);
		return lines;
	}

	public static String toInfo(AppNameType appnametype)
	{
		String searchableAppId = "[" + appnametype.appid() + "]";
		return searchableAppId + appnametype_or_missing(appnametype);
	}

	static String appnametype_or_missing(AppNameType appNameType)
	{
		return appNameType.appnametype().orElse(
			">>>>"
				+ "appinfo.vdf missing: " + appNameType.appid()
				+ "<<<<");
	}

	public static class AppInfoAppNameType implements AppNameType
	{

		@Override
		public AppId appid()
		{
			return appid;
		}

		@Override
		public Optional<String> appnametype()
		{
			return d_appinfo.get(appid)
				.map(
					appInfo -> AppNameTypes
						.appnametype(appInfo.name(), appInfo.type()));
		}

		public AppInfoAppNameType(final AppId appid,
			final Data_appinfo_vdf appinfo)
		{
			this.appid = appid;
			this.d_appinfo = appinfo;
		}

		private final AppId appid;

		private final Data_appinfo_vdf d_appinfo;

	}

}
