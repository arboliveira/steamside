package br.com.arbo.steamside.steam.store;

import br.com.arbo.steamside.types.AppId;
import br.com.arbo.steamside.types.AppName;

@Deprecated
public interface AppNameFactory {

	AppName nameOf(final AppId app);
}
