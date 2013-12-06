package br.com.arbo.steamside.apps;

import br.com.arbo.steamside.steam.client.localfiles.appcache.entry.AppInfo;
import br.com.arbo.steamside.steam.client.localfiles.appcache.entry.NotAvailableOnThisPlatform;
import br.com.arbo.steamside.steam.client.localfiles.appcache.inmemory.InMemory_appinfo_vdf;
import br.com.arbo.steamside.steam.client.localfiles.appcache.inmemory.NotFound;

public class FilterPlatform implements Filter {

	@Override
	public void consider(final App app) throws Reject {
		final AppInfo info = appinfo.get(app.appid().appid);
		try {
			info.executable();
		} catch (final NotAvailableOnThisPlatform e) {
			throw new Reject();
		} catch (final NotFound e) {
			return;
		}
	}

	public FilterPlatform(final InMemory_appinfo_vdf appinfo) {
		this.appinfo = appinfo;
	}

	private final InMemory_appinfo_vdf appinfo;

}
