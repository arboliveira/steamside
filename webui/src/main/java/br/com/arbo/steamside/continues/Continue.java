package br.com.arbo.steamside.continues;

import java.util.List;

import br.com.arbo.steamside.collection.CollectionFromVdf;
import br.com.arbo.steamside.collection.CollectionFromVdf.Filter;
import br.com.arbo.steamside.kids.KidsMode;
import br.com.arbo.steamside.steam.client.localfiles.appcache.InMemory_appinfo_vdf;
import br.com.arbo.steamside.steam.client.localfiles.sharedconfig.App;
import br.com.arbo.steamside.types.Category;
import br.com.arbo.steamside.webui.appdto.AppDTO;
import br.com.arbo.steamside.webui.wicket.SharedConfigConsume;

public class Continue implements Filter {

	private final SharedConfigConsume sharedconfig;
	private final KidsMode kidsmode;
	private final InMemory_appinfo_vdf appinfo;

	public Continue(
			final InMemory_appinfo_vdf appinfo,
			final SharedConfigConsume sharedconfig,
			final KidsMode kidsmode) {
		this.appinfo = appinfo;
		this.sharedconfig = sharedconfig;
		this.kidsmode = kidsmode;
	}

	public List<AppDTO> fetch() {
		// TODO Prioritize games launched by current user
		final CollectionFromVdf fromVdf =
				new CollectionFromVdf(appinfo, sharedconfig);
		return fromVdf.fetch(this).apps;
	}

	@Override
	public boolean pass(final App app) {
		if (!kidsmode.isKidsModeOn()) return true;
		return app.isInCategory(
				new Category(kidsmode.getCategoryAllowedToBeSeen()));
	}
}
