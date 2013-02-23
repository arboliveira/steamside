package br.com.arbo.steamside.vdf.examples;

import br.com.arbo.steamside.web.AppNameFactory;

public class SysoutAppInfoLine {

	private final AppNameFactory appnameFactory;

	public SysoutAppInfoLine(final AppNameFactory appnameFactory) {
		this.appnameFactory = appnameFactory;
	}

	public void sysout(final String app) {
		System.out.println(toInfo(app));
	}

	private String toInfo(final String app) {
		return app + "]" + appnameFactory.nameOf(app).name;
	}

}
