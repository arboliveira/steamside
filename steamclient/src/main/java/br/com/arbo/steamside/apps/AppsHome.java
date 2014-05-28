package br.com.arbo.steamside.apps;

import java.util.stream.Stream;

import br.com.arbo.steamside.steam.client.types.AppId;
import br.com.arbo.steamside.steam.client.types.SteamCategory;

public interface AppsHome extends AppsCollection {

	Stream<SteamCategory> allSteamCategories();

	int count(AppCriteria criteria);

	App find(AppId appid) throws NotFound;

	Stream<App> findIn(SteamCategory category);

}
