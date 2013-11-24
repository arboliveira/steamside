package br.com.arbo.steamside.continues;

import javax.inject.Inject;

import br.com.arbo.steamside.apps.App;
import br.com.arbo.steamside.apps.Filter;
import br.com.arbo.steamside.apps.FilterKidsMode;
import br.com.arbo.steamside.apps.FilterPlatform;
import br.com.arbo.steamside.kids.KidsMode;
import br.com.arbo.steamside.steam.client.localfiles.appcache.inmemory.InMemory_appinfo_vdf;

public class Continue implements Filter {

	@Override
	public void consider(final App app) throws Reject {
		new FilterKidsMode(kidsmode).consider(app);
		new FilterPlatform(appinfo).consider(app);
	}

	private final KidsMode kidsmode;
	private final InMemory_appinfo_vdf appinfo;

	@Inject
	public Continue(
			final InMemory_appinfo_vdf appinfo,
			final KidsMode kidsmode) {
		this.appinfo = appinfo;
		this.kidsmode = kidsmode;
	}

}
