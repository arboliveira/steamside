package br.com.arbo.steamside.steam.client.localfiles.appcache;

import br.com.arbo.steamside.steam.client.localfiles.appcache.InMemory_appinfo_vdf.NotFound;
import br.com.arbo.steamside.steam.store.AppName;
import br.com.arbo.steamside.steam.store.AppNameFactory;
import br.com.arbo.steamside.types.AppId;

public final class AppNameFromLocalFiles implements AppNameFactory {

	@Override
	public AppName nameOf(final AppId appid) throws NotFound {
		final AppInfo appInfo = this.appinfo_vdf.get(appid.appid);
		if (appInfo == null)
			throw new NullPointerException();
		return new AppName(appInfo.name);
	}

	private final InMemory_appinfo_vdf appinfo_vdf;

	public AppNameFromLocalFiles(final InMemory_appinfo_vdf appinfo_vdf) {
		this.appinfo_vdf = appinfo_vdf;
	}

}