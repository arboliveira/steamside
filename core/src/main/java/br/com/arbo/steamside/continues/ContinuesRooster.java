package br.com.arbo.steamside.continues;

import java.util.stream.Stream;

import br.com.arbo.steamside.steam.client.apps.App;

public interface ContinuesRooster {

	Stream<App> continues();

}
