package br.com.arbo.steamside.steam.store;

import br.com.arbo.steamside.types.AppId;

public interface AppNameFactory {

	AppName nameOf(final AppId app);
}
