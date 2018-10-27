package br.com.arbo.steamside.steam.client.apps.home;

import java.util.Optional;
import java.util.stream.Stream;

import br.com.arbo.steamside.steam.client.apps.App;
import br.com.arbo.steamside.steam.client.types.AppId;

public interface AppsHome
{

	int count(AppCriteria criteria);

	Stream<App> everyApp();

	Optional<App> find(AppId appid);

	Stream<App> stream(AppCriteria criteria);

}
