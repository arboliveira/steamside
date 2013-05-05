package br.com.arbo.steamside.collection;

import br.com.arbo.steamside.apps.App;
import br.com.arbo.steamside.steam.client.localfiles.appcache.AppInfo.NotAvailableOnThisPlatform;
import br.com.arbo.steamside.steam.client.localfiles.appcache.InMemory_appinfo_vdf;
import br.com.arbo.steamside.steam.client.localfiles.appcache.InMemory_appinfo_vdf.NotFound;

public class FilterPlatform implements Filter {

	@Override
	public void consider(final App app) throws Reject {
		try {
			appinfo.get(app.appid().appid).executable();
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
