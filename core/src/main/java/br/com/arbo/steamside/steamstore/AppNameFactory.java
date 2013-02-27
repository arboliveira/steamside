package br.com.arbo.steamside.steamstore;

import br.com.arbo.steamside.types.AppId;

public interface AppNameFactory {

	AppName nameOf(final AppId app);
}
