package br.com.arbo.steamside.steam.client.localfiles.appcache.inmemory;

import br.com.arbo.steamside.steam.store.AppNameFactory;
import br.com.arbo.steamside.types.AppId;

public class SysoutAppInfoLine {

	private final AppNameFactory appnameFactory;

	public SysoutAppInfoLine(final AppNameFactory appnameFactory) {
		this.appnameFactory = appnameFactory;
	}

	public String toInfo(final AppId app) {
		return app.appid + "]" + appname(app);
	}

	private String appname(final AppId app) {
		try {
			return appnameFactory.nameOf(app).name;
		} catch (final NotFound e) {
			return ">>>>" + e.getMessage() + "<<<<";
		}
	}

}
