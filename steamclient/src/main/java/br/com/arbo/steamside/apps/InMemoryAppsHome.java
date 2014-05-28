package br.com.arbo.steamside.apps;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Stream;

import org.eclipse.jdt.annotation.NonNull;

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
		return (int) select(criteria).count();
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
		if (AppCriteria.isAll(criteria)) return apps.values().stream();
		return select(criteria);
	}

	private Stream<App> select(@NonNull AppCriteria criteria)
	{
		Stream<App> s = apps.values().stream();
		if (criteria.gamesOnly)
			s = s.filter(App::isGame);
		return s;
	}

	final ArrayListMultimap<String, App> categories =
			ArrayListMultimap.<String, App> create();

	private final Map<String, App> apps = new HashMap<String, App>();

}
