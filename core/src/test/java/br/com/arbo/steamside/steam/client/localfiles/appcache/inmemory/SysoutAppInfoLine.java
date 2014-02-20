package br.com.arbo.steamside.steam.client.localfiles.appcache.inmemory;

import br.com.arbo.steamside.apps.App;
import br.com.arbo.steamside.apps.MissingFrom_appinfo_vdf;
import br.com.arbo.steamside.steam.store.AppNameFactory;

public class SysoutAppInfoLine {

	private final AppNameFactory appnameFactory;

	public SysoutAppInfoLine(final AppNameFactory appnameFactory) {
		this.appnameFactory = appnameFactory;
	}

	public String toInfo(final App app) {
		return app.appid() + "]" + appname(app);
	}

	private String appname(final App app) {
		try {
			return app.name();
		} catch (final MissingFrom_appinfo_vdf e) {
			return ">>>>" + e.getMessage() + "<<<<";
		}
	}

}
