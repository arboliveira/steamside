package br.com.arbo.steamside.steam.client.localfiles.appcache.inmemory;

import br.com.arbo.steamside.steam.client.localfiles.appcache.entry.AppInfo;

public interface I_appinfo_vdf {

	AppInfo get(final String appid);

}
