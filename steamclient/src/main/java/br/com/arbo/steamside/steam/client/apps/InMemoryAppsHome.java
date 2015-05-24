package br.com.arbo.steamside.steam.client.apps;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Stream;

import com.google.common.collect.ArrayListMultimap;

import br.com.arbo.steamside.steam.client.types.AppId;
import br.com.arbo.steamside.steam.client.types.SteamCategory;

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
		return Optional
			.ofNullable(
				apps.get(appid.appid))
			.orElseThrow(
				() -> NotFound.appid(appid.appid));
	}

	@Override
	public Stream<App> findIn(SteamCategory category)
	{
		return Optional
			.ofNullable(
				categories.get(category.category))
			.map(
				List::stream)
			.orElseThrow(
				() -> new SteamCategory.NotFound());
	}

	@Override
	public Stream<App> stream(AppCriteria criteria)
	{
		Stream<App> stream = apps.values().stream();
		if (AppCriteria.isAll(criteria))
			return stream;
		return criteria.filter(stream);
	}

	final ArrayListMultimap<String, App> categories = ArrayListMultimap
		.<String, App> create();

	private final Map<String, App> apps = new HashMap<String, App>();

}
