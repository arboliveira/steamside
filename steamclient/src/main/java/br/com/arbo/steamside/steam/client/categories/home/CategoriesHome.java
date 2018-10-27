package br.com.arbo.steamside.steam.client.categories.home;

import java.util.stream.Stream;

import br.com.arbo.steamside.steam.client.categories.category.SteamCategory;

public interface CategoriesHome
{

	Stream<SteamCategory> everySteamCategory();

}
