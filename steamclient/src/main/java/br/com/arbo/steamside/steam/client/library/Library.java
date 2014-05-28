package br.com.arbo.steamside.steam.client.library;

import java.util.stream.Stream;

import br.com.arbo.steamside.steam.client.apps.App;
import br.com.arbo.steamside.steam.client.apps.AppCriteria;
import br.com.arbo.steamside.steam.client.apps.NotFound;
import br.com.arbo.steamside.steam.client.types.AppId;
import br.com.arbo.steamside.steam.client.types.SteamCategory;

public interface Library {

	Stream<App> allApps(AppCriteria criteria);

	Stream<SteamCategory> allSteamCategories();

	int count(AppCriteria criteria);

	App find(AppId appid) throws NotFound;

	Stream<App> findIn(SteamCategory category);

}
