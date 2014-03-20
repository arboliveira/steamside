package br.com.arbo.steamside.apps;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Stream;

import br.com.arbo.steamside.types.AppId;
import br.com.arbo.steamside.types.Category;

import com.google.common.collect.ArrayListMultimap;

public class InMemoryAppsHome implements AppsHome {

	public void add(final App app)
	{
		apps.put(app.appid().appid, app);
		app.forEachCategory(each -> categories.put(each.category, app));
	}

	@Override
	public Stream<Category> allSteamCategories()
	{
		return categories.keySet().stream().map(Category::new);
	}

	@Override
	public int count()
	{
		return apps.size();
	}

	@Override
	public App find(final AppId appid) throws NotFound
	{
		final App app = apps.get(appid.appid);
		if (app != null) return app;
		throw NotFound.appid(appid.appid);
	}

	@Override
	public Stream<App> findIn(Category category)
	{
		final List<App> c = categories.get(category.category);
		if (c == null)
			throw new br.com.arbo.steamside.data.collections.NotFound();
		return c.stream();
	}

	@Override
	public Stream<App> stream()
	{
		return apps.values().stream();
	}

	final ArrayListMultimap<String, App> categories =
			ArrayListMultimap.<String, App> create();

	private final Map<String, App> apps = new HashMap<String, App>();

}
