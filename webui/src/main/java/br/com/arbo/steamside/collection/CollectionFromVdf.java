package br.com.arbo.steamside.collection;

import java.util.ArrayList;
import java.util.List;

import br.com.arbo.steamside.steam.store.AppNameFactory;
import br.com.arbo.steamside.types.AppId;
import br.com.arbo.steamside.types.Category;
import br.com.arbo.steamside.vdf.App;
import br.com.arbo.steamside.vdf.Apps;
import br.com.arbo.steamside.vdf.Apps.AppIdVisitor;
import br.com.arbo.steamside.vdf.NotFound;
import br.com.arbo.steamside.vdf.Factory_sharedconfig_vdf;
import br.com.arbo.steamside.webui.appdto.AppCollectionDTO;
import br.com.arbo.steamside.webui.appdto.AppDTO;

public class CollectionFromVdf {

	private final AppNameFactory namefactory;

	public CollectionFromVdf(final AppNameFactory namefactory) {
		this.namefactory = namefactory;
	}

	public AppCollectionDTO fetch(final Category name) {

		final Apps apps = Factory_sharedconfig_vdf.fromFile().apps();
		final CollectionFromVdf.Filter filter = new Filter(name, apps,
				namefactory);
		apps.accept(filter);

		final AppCollectionDTO results = new AppCollectionDTO();
		results.apps = filter.list;
		return results;
	}

	static class Filter implements AppIdVisitor {

		private final Category name;
		private final Apps apps;
		final List<AppDTO> list = new ArrayList<AppDTO>(20);
		private final AppNameFactory namefactory;

		public Filter(final Category name, final Apps appsImpl,
				final AppNameFactory namefactory) {
			this.name = name;
			this.apps = appsImpl;
			this.namefactory = namefactory;
		}

		@Override
		public void each(final AppId appid) {
			final App app = app(appid);
			if (app.isInCategory(name)) {
				final AppDTO dto =
						new AppDTO(appid,
								this.namefactory.nameOf(appid).name);
				list.add(dto);
			}
		}

		private App app(final AppId appid) throws Error {
			try {
				return apps.app(appid);
			} catch (final NotFound e) {
				throw new Error("cannot happen");
			}
		}
	}
}