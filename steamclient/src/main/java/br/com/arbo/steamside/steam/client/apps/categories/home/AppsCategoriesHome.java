package br.com.arbo.steamside.steam.client.apps.categories.home;

import java.util.stream.Stream;

import br.com.arbo.steamside.steam.client.apps.App;
import br.com.arbo.steamside.steam.client.categories.category.SteamCategory;

public interface AppsCategoriesHome
{

	Stream<App> findIn(SteamCategory category);

}
