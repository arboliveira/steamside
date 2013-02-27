package br.com.arbo.steamside.vdf.examples;

import br.com.arbo.steamside.steam.store.AppNameFactory;
import br.com.arbo.steamside.types.AppId;

public class SysoutAppInfoLine {

	private final AppNameFactory appnameFactory;

	public SysoutAppInfoLine(final AppNameFactory appnameFactory) {
		this.appnameFactory = appnameFactory;
	}

	public void sysout(final AppId app) {
		System.out.println(toInfo(app));
	}

	private String toInfo(final AppId app) {
		return app.appid + "]" + appnameFactory.nameOf(app).name;
	}

}
