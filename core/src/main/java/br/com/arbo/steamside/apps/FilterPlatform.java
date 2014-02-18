package br.com.arbo.steamside.apps;

import br.com.arbo.steamside.steam.client.localfiles.appcache.entry.NotAvailableOnThisPlatform;

public class FilterPlatform implements Filter {

	@Override
	public void consider(final App app) throws Reject {
		try {
			app.executable();
		} catch (final NotAvailableOnThisPlatform e) {
			throw new Reject();
		}
	}

}
