package br.com.arbo.steamside.steam.client.localfiles.appcache.inmemory;

import br.com.arbo.steamside.steam.client.localfiles.appcache.entry.AppInfo;
import br.com.arbo.steamside.types.AppId;

public interface I_appinfo_vdf {

	AppInfo get(final AppId appid) throws NotFound;

}
