package br.com.arbo.steamside.collection;

import java.util.ArrayList;
import java.util.List;

import br.com.arbo.steamside.collection.Filter.Reject;
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
		// TODO Prioritize games launched by current user
		final List<App> list = populate(filter);
		return toDTO(list);
	}

	private AppCollectionDTO toDTO(final List<App> list) {
		return new ToDTO(appinfo).sortLimitConvert(list);
	}

	private List<App> populate(final Filter filter) {
		final Apps apps = sharedconfig.data().apps();
		final List<App> list = new ArrayList<App>(apps.count());
		filter(apps, list, filter);
		return list;
	}

	private static void filter(
			final Apps apps, final List<App> list, final Filter filter) {
		class Consider implements AppVisitor {

			@Override
			public void each(final App app) {
				if (consider(app)) list.add(app);
			}

			private boolean consider(final App app) {
				if (filter != null)
					try {
						filter.consider(app);
					} catch (final Reject e) {
						return false;
					}
				return true;
			}
		}
		apps.accept(new Consider());
	}
}