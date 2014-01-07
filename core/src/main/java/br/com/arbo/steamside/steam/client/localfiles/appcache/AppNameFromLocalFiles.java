package br.com.arbo.steamside.steam.client.localfiles.appcache;

import javax.inject.Inject;

import br.com.arbo.steamside.steam.client.localfiles.appcache.entry.AppInfo;
import br.com.arbo.steamside.steam.client.localfiles.appcache.inmemory.InMemory_appinfo_vdf;
import br.com.arbo.steamside.steam.store.AppNameFactory;
import br.com.arbo.steamside.types.AppId;
import br.com.arbo.steamside.types.AppName;

public final class AppNameFromLocalFiles implements AppNameFactory {

	@Override
	public AppName nameOf(final AppId appid) {
		final AppInfo appInfo = this.appinfo_vdf.get(appid);
		if (appInfo == null)
			throw new NullPointerException();
		return appInfo.name();
	}

	private final InMemory_appinfo_vdf appinfo_vdf;

	@Inject
	public AppNameFromLocalFiles(final InMemory_appinfo_vdf appinfo_vdf) {
		this.appinfo_vdf = appinfo_vdf;
	}

}