package br.com.arbo.steamside.collection;

import java.util.ArrayList;
import java.util.List;

import br.com.arbo.steamside.steam.client.localfiles.appcache.InMemory_appinfo_vdf;
import br.com.arbo.steamside.steam.client.localfiles.sharedconfig.App;
import br.com.arbo.steamside.steam.client.localfiles.sharedconfig.Apps;
import br.com.arbo.steamside.steam.client.localfiles.sharedconfig.Apps.AppVisitor;
import br.com.arbo.steamside.webui.appdto.AppCollectionDTO;
import br.com.arbo.steamside.webui.wicket.SharedConfigConsume;

public class CollectionFromVdf {

	private final InMemory_appinfo_vdf appinfo;
	private final SharedConfigConsume sharedconfig;

	public CollectionFromVdf(
			final InMemory_appinfo_vdf appinfo,
			final SharedConfigConsume sharedconfig) {
		this.appinfo = appinfo;
		this.sharedconfig = sharedconfig;
	}

	public AppCollectionDTO fetchAll() {
		final List<App> list = populate(null);
		return toDTO(list);
	}

	public AppCollectionDTO fetch(final Filter filter) {
		final List<App> list = populate(filter);
		return toDTO(list);
	}

	private AppCollectionDTO toDTO(final List<App> list) {
		return new ToDTO(appinfo).sortLimitConvert(list);
	}

	private List<App> populate(final Filter filter) {
		final Apps apps = sharedconfig.data().apps();
		final List<App> list = new ArrayList<App>(apps.count());
		filterCategory(apps, list, filter);
		return list;
	}

	public interface Filter {

		boolean pass(App app);
	}

	private static void filterCategory(final Apps apps, final List<App> list,
			final Filter filter) {
		apps.accept(new AppVisitor() {

			@Override
			public void each(final App app) {
				if (filter == null || filter.pass(app)) list.add(app);
			}
		});
	}
}