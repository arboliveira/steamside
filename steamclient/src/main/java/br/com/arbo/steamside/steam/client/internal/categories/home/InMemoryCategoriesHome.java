package br.com.arbo.steamside.steam.client.internal.categories.home;

import java.util.stream.Stream;

import br.com.arbo.steamside.steam.client.categories.category.SteamCategory;
import br.com.arbo.steamside.steam.client.categories.home.CategoriesHome;
import br.com.arbo.steamside.steam.client.internal.apps.categories.home.InMemoryAppsCategoriesHome;

public class InMemoryCategoriesHome implements CategoriesHome
{

	@Override
	public Stream<SteamCategory> everySteamCategory()
	{
		return apps_categories.allSteamCategories();
	}

	public InMemoryCategoriesHome(InMemoryAppsCategoriesHome categories)
	{
		this.apps_categories = categories;
	}

	private final InMemoryAppsCategoriesHome apps_categories;

}
