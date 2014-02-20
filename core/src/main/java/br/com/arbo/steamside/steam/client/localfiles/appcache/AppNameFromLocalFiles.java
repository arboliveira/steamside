package br.com.arbo.steamside.steam.client.localfiles.appcache;

import javax.inject.Inject;

import br.com.arbo.steamside.steam.client.localfiles.appcache.entry.AppInfo;
import br.com.arbo.steamside.steam.client.localfiles.appcache.inmemory.Data_appinfo_vdf;
import br.com.arbo.steamside.steam.store.AppNameFactory;
import br.com.arbo.steamside.types.AppId;
import br.com.arbo.steamside.types.AppName;

@Deprecated
public final class AppNameFromLocalFiles implements AppNameFactory {

	@Override
	public AppName nameOf(final AppId appid) {
		final AppInfo appInfo = this.appinfo_vdf.get(appid);
		if (appInfo == null)
			throw new NullPointerException();
		return appInfo.name();
	}

	private final Data_appinfo_vdf appinfo_vdf;

	@Inject
	public AppNameFromLocalFiles(final Data_appinfo_vdf appinfo_vdf) {
		this.appinfo_vdf = appinfo_vdf;
	}

}