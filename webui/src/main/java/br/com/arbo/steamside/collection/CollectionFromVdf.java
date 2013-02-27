package br.com.arbo.steamside.collection;

import java.util.ArrayList;
import java.util.List;

import br.com.arbo.steamside.steam.store.AppName;
import br.com.arbo.steamside.steam.store.AppNameFactory;
import br.com.arbo.steamside.types.AppId;
import br.com.arbo.steamside.types.Category;
import br.com.arbo.steamside.vdf.App;
import br.com.arbo.steamside.vdf.Apps;
import br.com.arbo.steamside.vdf.Apps.AppVisitor;
import br.com.arbo.steamside.webui.appdto.AppCollectionDTO;
import br.com.arbo.steamside.webui.appdto.AppDTO;
import br.com.arbo.steamside.webui.wicket.SharedConfigConsume;

public class CollectionFromVdf {

	private final AppNameFactory namefactory;
	private final SharedConfigConsume sharedconfig;

	public CollectionFromVdf(
			final AppNameFactory namefactory,
			final SharedConfigConsume sharedconfig) {
		this.namefactory = namefactory;
		this.sharedconfig = sharedconfig;
	}

	public AppCollectionDTO fetch(final Category category) {
		final List<AppDTO> list = new ArrayList<AppDTO>(20);

		class Filter implements AppVisitor {

			@Override
			public void each(final App app) {
				if (!app.isInCategory(category)) return;
				final AppId appid = app.appid();
				final AppDTO dto =
						new AppDTO(appid, nameOf(appid));
				list.add(dto);
			}
		}

		final Apps apps = sharedconfig.data().apps();
		apps.accept(new Filter());

		final AppCollectionDTO results = new AppCollectionDTO();
		results.apps = list;
		return results;
	}

	AppName nameOf(final AppId appid) {
		return namefactory.nameOf(appid);
	}
}