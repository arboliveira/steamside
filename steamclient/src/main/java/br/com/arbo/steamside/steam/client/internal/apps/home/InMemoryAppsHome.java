package br.com.arbo.steamside.steam.client.internal.apps.home;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Stream;

import br.com.arbo.steamside.steam.client.apps.App;
import br.com.arbo.steamside.steam.client.apps.home.AppCriteria;
import br.com.arbo.steamside.steam.client.apps.home.AppsHome;
import br.com.arbo.steamside.steam.client.types.AppId;
import br.com.arbo.steamside.steam.client.types.LastPlayed;

public class InMemoryAppsHome implements AppsHome
{

	public void add(App app)
	{
		apps.put(app.appid().appid, app);
	}

	@Override
	public int count(AppCriteria criteria)
	{
		if (AppCriteria.isAll(criteria)) return apps.size();
		return (int) filter(apps.values().stream(), criteria).count();
	}

	@Override
	public Stream<App> everyApp()
	{
		return apps.values().stream();
	}

	@Override
	public Optional<App> find(AppId appid)
	{
		return Optional.ofNullable(apps.get(appid.appid));
	}

	@Override
	public Stream<App> find(AppCriteria criteria)
	{
		return filter(apps.values().stream(), criteria);
	}

	@Override
	public Stream<App> find(
		Stream<AppId> in, AppCriteria criteria)
	{
		return filter(
			present(in).map(appid -> apps.get(appid.appid)), criteria);
	}

	@Override
	public Map<AppId, Optional<App>> match(
		Stream<AppId> in, AppCriteria criteria)
	{
		Map<AppId, Optional<App>> m = new HashMap<>();

		present(in).forEach(
			appid -> m.put(appid, match(appid, criteria)));

		return m;
	}

	private Optional<App> match(AppId appid, AppCriteria criteria)
	{
		App app = apps.get(appid.appid);

		return app.matches(criteria) ? Optional.of(app) : Optional.empty();
	}

	private Stream<AppId> present(Stream<AppId> in)
	{
		return in.filter(appid -> apps.containsKey(appid.appid));
	}

	private static Stream<App> filter(Stream<App> stream, AppCriteria criteria)
	{
		if (AppCriteria.isAll(criteria)) return stream;

		return lastPlayedDescending(
			stream.filter(app -> app.matches(criteria)), criteria);
	}

	private static Stream<App> lastPlayedDescending(
		Stream<App> stream, AppCriteria criteria)
	{
		if (criteria.lastPlayedDescending())
		{
			return stream.sorted(LastPlayed.descending(App::lastPlayed));
		}

		return stream;
	}

	private final Map<String, App> apps = new HashMap<String, App>();

}
