package br.com.arbo.steamside.steam.store;

import br.com.arbo.steamside.types.AppId;

public class App {

	public final AppId appid;
	public final AppName name;

	public App(final AppId appid, final AppName name) {
		this.appid = appid;
		this.name = name;
	}

}
