package br.com.arbo.steamside.steam.client.home;

import br.com.arbo.steamside.steam.client.apps.categories.home.AppsCategoriesHome;
import br.com.arbo.steamside.steam.client.apps.home.AppsHome;
import br.com.arbo.steamside.steam.client.categories.home.CategoriesHome;

public interface SteamClientHome
{

	AppsHome apps();

	AppsCategoriesHome apps_categories();

	CategoriesHome categories();

}
