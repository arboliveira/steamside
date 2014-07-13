package br.com.arbo.steamside.steam.client.apps;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Stream;

import br.com.arbo.steamside.steam.client.types.AppId;
import br.com.arbo.steamside.steam.client.types.SteamCategory;

import com.google.common.collect.ArrayListMultimap;

public class InMemoryAppsHome implements AppsHome {

	public void add(final App app)
	{
		apps.put(app.appid().appid, app);
		app.forEachCategory(each -> categories.put(each.category, app));
	}

	@Override
	public Stream<SteamCategory> allSteamCategories()
	{
		return categories.keySet().stream().map(SteamCategory::new);
	}

	@Override
	public int count(AppCriteria criteria)
	{
		if (AppCriteria.isAll(criteria)) return apps.size();
		return (int) criteria.filter(apps.values().stream()).count();
	}

	@Override
	public App find(final AppId appid) throws NotFound
	{
		final App app = apps.get(appid.appid);
		if (app != null) return app;
		throw NotFound.appid(appid.appid);
	}

	@Override
	public Stream<App> findIn(SteamCategory category)
	{
		final List<App> c = categories.get(category.category);
		if (c == null)
			throw new SteamCategory.NotFound();
		return c.stream();
	}

	@Override
	public Stream<App> stream(AppCriteria criteria)
	{
		final Stream<App> stream = apps.values().stream();
		if (AppCriteria.isAll(criteria)) return stream;
		return criteria.filter(stream);
	}

	final ArrayListMultimap<String, App> categories =
			ArrayListMultimap.<String, App> create();

	private final Map<String, App> apps = new HashMap<String, App>();

}
