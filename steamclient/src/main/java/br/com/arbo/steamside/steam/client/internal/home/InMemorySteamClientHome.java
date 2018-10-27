package br.com.arbo.steamside.steam.client.internal.home;

import br.com.arbo.steamside.steam.client.apps.App;
import br.com.arbo.steamside.steam.client.apps.categories.home.AppsCategoriesHome;
import br.com.arbo.steamside.steam.client.apps.home.AppsHome;
import br.com.arbo.steamside.steam.client.categories.home.CategoriesHome;
import br.com.arbo.steamside.steam.client.home.SteamClientHome;
import br.com.arbo.steamside.steam.client.internal.apps.categories.home.InMemoryAppsCategoriesHome;
import br.com.arbo.steamside.steam.client.internal.apps.home.InMemoryAppsHome;
import br.com.arbo.steamside.steam.client.internal.categories.home.InMemoryCategoriesHome;

public class InMemorySteamClientHome implements SteamClientHome
{

	public void add(final App app)
	{
		apps.add(app);
		app.forEachCategory(each -> apps_categories.put(each.category, app));
	}

	@Override
	public AppsHome apps()
	{
		return apps;
	}

	@Override
	public AppsCategoriesHome apps_categories()
	{
		return apps_categories;
	}

	@Override
	public CategoriesHome categories()
	{
		return categories;
	}

	public InMemorySteamClientHome()
	{
		apps = new InMemoryAppsHome();
		apps_categories = new InMemoryAppsCategoriesHome();
		categories = new InMemoryCategoriesHome(apps_categories);
	}

	private final InMemoryAppsHome apps;
	private final InMemoryAppsCategoriesHome apps_categories;
	private final InMemoryCategoriesHome categories;

}
