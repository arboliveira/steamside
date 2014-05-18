package br.com.arbo.steamside.library;

import java.util.stream.Stream;

import br.com.arbo.steamside.apps.App;
import br.com.arbo.steamside.apps.NotFound;
import br.com.arbo.steamside.types.AppId;
import br.com.arbo.steamside.types.SteamCategory;

public interface Library {

	Stream<App> allApps();

	Stream<SteamCategory> allSteamCategories();

	int count();

	App find(AppId appid) throws NotFound;

	Stream<App> findIn(SteamCategory category);

}
