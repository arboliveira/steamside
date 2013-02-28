package br.com.arbo.steamside.collection;

import java.util.ArrayList;
import java.util.List;

import br.com.arbo.steamside.steam.client.localfiles.sharedconfig.App;
import br.com.arbo.steamside.steam.client.localfiles.sharedconfig.Apps;
import br.com.arbo.steamside.steam.client.localfiles.sharedconfig.Apps.AppVisitor;
import br.com.arbo.steamside.steam.store.AppName;
import br.com.arbo.steamside.steam.store.AppNameFactory;
import br.com.arbo.steamside.types.AppId;
import br.com.arbo.steamside.types.Category;
import br.com.arbo.steamside.webui.appdto.AppCollectionDTO;
import br.com.arbo.steamside.webui.appdto.AppDTO;
import br.com.arbo.steamside.webui.appdto.Size;
import br.com.arbo.steamside.webui.appdto.Visible;
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

		final Apps apps = sharedconfig.data().apps();
		apps.accept(new AppVisitor() {

			int i = 0;

			@Override
			public void each(final App app) {
				if (!app.isInCategory(category)) return;
				i++;
				final AppId appid = app.appid();
				final AppName appname = nameOf(appid);
				final Size size = i == 1 ? Size.Large : Size.Regular;
				final Visible visible = i <= 3 ? Visible.True : Visible.False;
				list.add(new AppDTO(appid, appname, size, visible));
			}
		});

		final AppCollectionDTO results = new AppCollectionDTO();
		results.apps = list;
		return results;
	}

	AppName nameOf(final AppId appid) {
		return namefactory.nameOf(appid);
	}
}