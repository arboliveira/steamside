package br.com.arbo.steamside.apps;

import br.com.arbo.steamside.steam.client.localfiles.appcache.entry.AppInfo;
import br.com.arbo.steamside.steam.client.localfiles.appcache.entry.NotAvailableOnThisPlatform;
import br.com.arbo.steamside.steam.client.localfiles.appcache.inmemory.I_appinfo_vdf;
import br.com.arbo.steamside.steam.client.localfiles.appcache.inmemory.NotFound;

public class FilterPlatform implements Filter {

	@Override
	public void consider(final App app) throws Reject {
		final String appid = app.appid().appid;
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

	public FilterPlatform(final I_appinfo_vdf appinfo) {
		this.appinfo = appinfo;
	}

	private final I_appinfo_vdf appinfo;

}
