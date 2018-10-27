package br.com.arbo.steamside.steam.client.internal.apps.categories.home;

import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

import com.google.common.collect.ArrayListMultimap;

import br.com.arbo.steamside.steam.client.apps.App;
import br.com.arbo.steamside.steam.client.apps.categories.home.AppsCategoriesHome;
import br.com.arbo.steamside.steam.client.categories.category.SteamCategory;
import br.com.arbo.steamside.steam.client.categories.home.CategoryNotFound;

public class InMemoryAppsCategoriesHome implements AppsCategoriesHome
{

	public Stream<SteamCategory> allSteamCategories()
	{
		return category_apps.keySet().stream().map(SteamCategory::new);
	}

	@Override
	public Stream<App> findIn(SteamCategory category)
	{
		return Optional
			.ofNullable(
				category_apps.get(category.category))
			.map(
				List::stream)
			.orElseThrow(
				() -> new CategoryNotFound());
	}

	public void put(String category, App app)
	{
		category_apps.put(category, app);
	}

	private final ArrayListMultimap<String, App> category_apps =
		ArrayListMultimap.<String, App> create();

}
