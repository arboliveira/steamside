package br.com.arbo.steamside.steam.client.localfiles.appcache.inmemory;

import br.com.arbo.steamside.apps.MissingFrom_appinfo_vdf;
import br.com.arbo.steamside.types.AppId;

public class SysoutAppInfoLine {

	private final Data_appinfo_vdf appinfo;

	public SysoutAppInfoLine(final Data_appinfo_vdf appinfo) {
		this.appinfo = appinfo;
	}

	public String toInfo(final AppId appid) {
		return appid + "]" + appname(appid);
	}

	private String appname(final AppId appid) {
		try {
			return appinfo.get(appid).name().name;
		} catch (final MissingFrom_appinfo_vdf e) {
			return ">>>>" + e.getMessage() + "<<<<";
		}
	}

}
