package br.com.arbo.steamside.apps;

import java.util.stream.Stream;

import br.com.arbo.steamside.types.AppId;
import br.com.arbo.steamside.types.Category;

public interface AppsHome extends AppsCollection {

	Stream<Category> allSteamCategories();

	int count();

	App find(AppId appid) throws NotFound;

	Stream<App> findIn(Category category);

}
