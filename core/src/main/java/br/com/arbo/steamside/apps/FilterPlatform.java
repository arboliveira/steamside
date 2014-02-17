package br.com.arbo.steamside.apps;

import br.com.arbo.steamside.steam.client.localfiles.appcache.entry.AppInfo;
import br.com.arbo.steamside.steam.client.localfiles.appcache.entry.NotAvailableOnThisPlatform;
import br.com.arbo.steamside.steam.client.localfiles.appcache.inmemory.Data_appinfo_vdf;
import br.com.arbo.steamside.steam.client.localfiles.appcache.inmemory.NotFound;
import br.com.arbo.steamside.types.AppId;

public class FilterPlatform implements Filter {

	@Override
	public void consider(final App app) throws Reject {
		final AppId appid = app.appid();
		final AppInfo info;
		try {
			info = appinfo.get(appid);
		} catch (final NotFound e) {
			return;
		}
		try {
			info.executable();
		} catch (final NotAvailableOnThisPlatform e) {
			throw new Reject();
		}
	}

	public FilterPlatform(final Data_appinfo_vdf appinfo) {
		this.appinfo = appinfo;
	}

	private final Data_appinfo_vdf appinfo;

}
