package br.com.arbo.steamside.web;

import br.com.arbo.steamside.steam.store.AppName;
import br.com.arbo.steamside.steam.store.AppNameFactory;
import br.com.arbo.steamside.steam.store.AppPage;
import br.com.arbo.steamside.types.AppId;

public class AppNameFromSteamStore implements AppNameFactory {

	@Override
	public AppName nameOf(final AppId app) {
		return new AppPage(app).name();
	}
}
