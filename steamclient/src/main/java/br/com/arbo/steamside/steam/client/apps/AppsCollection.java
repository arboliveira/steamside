package br.com.arbo.steamside.steam.client.apps;

import java.util.stream.Stream;

public interface AppsCollection {

	Stream<App> stream(AppCriteria criteria);
}