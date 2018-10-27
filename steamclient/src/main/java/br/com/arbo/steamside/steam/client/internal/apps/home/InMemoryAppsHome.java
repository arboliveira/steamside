package br.com.arbo.steamside.steam.client.internal.apps.home;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import br.com.arbo.steamside.steam.client.apps.App;
import br.com.arbo.steamside.steam.client.apps.home.AppCriteria;
import br.com.arbo.steamside.steam.client.apps.home.AppsHome;
import br.com.arbo.steamside.steam.client.types.AppId;

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
	public Optional<App> find(final AppId appid)
	{
		return Optional.ofNullable(apps.get(appid.appid));
	}

	@Override
	public Stream<App> stream(AppCriteria criteria)
	{
		Stream<App> stream = apps.values().stream();

		if (AppCriteria.isAll(criteria))
			return stream;

		return filter(stream, criteria);
	}

	private static Stream<App> filter(Stream<App> stream, AppCriteria c)
	{
		Stream<App> s = stream;

		if (c.gamesOnly())
			s = s.filter(App::isGame);

		if (c.in() != null)
		{
			Set<AppId> set = c.in().collect(Collectors.toSet());
			s = s.filter(app -> set.contains(app.appid()));
		}

		if (c.owned())
			s = s.filter(App::isOwned);

		if (c.currentPlatformOnly())
			s = s.filter(new FilterPlatform());

		return s;
	}

	private final Map<String, App> apps = new HashMap<String, App>();

}
