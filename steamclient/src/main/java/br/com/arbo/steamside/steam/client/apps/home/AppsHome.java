package br.com.arbo.steamside.steam.client.apps.home;

import java.util.Map;
import java.util.Optional;
import java.util.stream.Stream;

import br.com.arbo.steamside.steam.client.apps.App;
import br.com.arbo.steamside.steam.client.types.AppId;

public interface AppsHome
{

	int count(AppCriteria criteria);

	Stream<App> everyApp();

	Optional<App> find(AppId appid);

	Stream<App> find(Stream<AppId> in, AppCriteria criteria);

	Stream<App> find(AppCriteria criteria);

	Map<AppId, Optional<App>> match(Stream<AppId> in, AppCriteria criteria);

}
