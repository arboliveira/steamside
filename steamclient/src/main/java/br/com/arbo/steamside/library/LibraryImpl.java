package br.com.arbo.steamside.library;

import java.util.stream.Stream;

import javax.inject.Inject;

import br.com.arbo.steamside.apps.App;
import br.com.arbo.steamside.apps.AppCriteria;
import br.com.arbo.steamside.apps.AppsHome;
import br.com.arbo.steamside.apps.AppsHomeFactory;
import br.com.arbo.steamside.apps.NotFound;
import br.com.arbo.steamside.types.AppId;
import br.com.arbo.steamside.types.SteamCategory;

public class LibraryImpl implements Library {

	@Inject
	public LibraryImpl(AppsHomeFactory appsHomeFactory)
	{
		this.appsHomeFactory = appsHomeFactory;
	}

	@Override
	public Stream<App> allApps(AppCriteria criteria)
	{
		return apps().stream(criteria);
	}

	@Override
	public Stream<SteamCategory> allSteamCategories()
	{
		return apps().allSteamCategories();
	}

	@Override
	public int count(AppCriteria criteria)
	{
		return apps().count(criteria);
	}

	@Override
	public App find(AppId appid) throws NotFound
	{
		return apps().find(appid);
	}

	@Override
	public Stream<App> findIn(SteamCategory category)
	{
		return apps().findIn(category);
	}

	private final AppsHome apps()
	{
		return appsHomeFactory.get();
	}

	private final AppsHomeFactory appsHomeFactory;

}
