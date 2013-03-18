package br.com.arbo.steamside.steam.store;

import br.com.arbo.steamside.steam.client.localfiles.appcache.InMemory_appinfo_vdf.NotFound;
import br.com.arbo.steamside.types.AppId;
import br.com.arbo.steamside.types.AppName;

public interface AppNameFactory {

	AppName nameOf(final AppId app) throws NotFound;
}
