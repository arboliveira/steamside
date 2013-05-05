package br.com.arbo.steamside.continues;

import br.com.arbo.steamside.apps.App;
import br.com.arbo.steamside.collection.Filter;
import br.com.arbo.steamside.collection.FilterKidsMode;
import br.com.arbo.steamside.collection.FilterPlatform;
import br.com.arbo.steamside.kids.KidsMode;
import br.com.arbo.steamside.steam.client.localfiles.appcache.InMemory_appinfo_vdf;

public class Continue implements Filter {

	private final KidsMode kidsmode;
	private final InMemory_appinfo_vdf appinfo;

	public Continue(
			final InMemory_appinfo_vdf appinfo,
			final KidsMode kidsmode) {
		this.appinfo = appinfo;
		this.kidsmode = kidsmode;
	}

	@Override
	public void consider(final App app) throws Reject {
		new FilterKidsMode(kidsmode).consider(app);
		new FilterPlatform(appinfo).consider(app);
	}

}
