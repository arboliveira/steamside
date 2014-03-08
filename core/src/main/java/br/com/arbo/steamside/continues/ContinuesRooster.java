package br.com.arbo.steamside.continues;

import java.util.function.Consumer;

import br.com.arbo.steamside.apps.App;

public interface ContinuesRooster {

	void accept(final Consumer<App> visitor);

}
