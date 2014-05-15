package br.com.arbo.steamside.apps;

import java.util.stream.Stream;

import br.com.arbo.steamside.types.AppId;
import br.com.arbo.steamside.types.SteamCategory;

public interface AppsHome extends AppsCollection {

	Stream<SteamCategory> allSteamCategories();

	int count();

	App find(AppId appid) throws NotFound;

	Stream<App> findIn(SteamCategory category);

}
