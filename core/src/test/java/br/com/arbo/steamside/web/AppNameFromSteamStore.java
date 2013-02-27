package br.com.arbo.steamside.web;

import br.com.arbo.steamside.steamstore.AppName;
import br.com.arbo.steamside.steamstore.AppNameFactory;
import br.com.arbo.steamside.steamstore.AppPage;
import br.com.arbo.steamside.types.AppId;

public class AppNameFromSteamStore implements AppNameFactory {

	@Override
	public AppName nameOf(final AppId app) {
		return new AppPage(app).name();
	}
}
