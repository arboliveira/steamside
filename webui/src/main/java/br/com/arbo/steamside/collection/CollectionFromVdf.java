package br.com.arbo.steamside.collection;

import java.util.ArrayList;
import java.util.List;

import br.com.arbo.steamside.vdf.App;
import br.com.arbo.steamside.vdf.Apps;
import br.com.arbo.steamside.vdf.Apps.Visitor;
import br.com.arbo.steamside.vdf.NotFound;
import br.com.arbo.steamside.vdf.SharedconfigVdfLocation;
import br.com.arbo.steamside.webui.appdto.AppCollectionDTO;
import br.com.arbo.steamside.webui.appdto.AppDTO;

public class CollectionFromVdf {

	public static AppCollectionDTO fetch(final String name) {

		final Apps apps = SharedconfigVdfLocation.make().apps();
		final CollectionFromVdf.Filter filter = new Filter(name, apps);
		apps.accept(filter);

		final AppCollectionDTO results = new AppCollectionDTO();
		results.apps = filter.list;
		return results;
	}

	static class Filter implements Visitor {

		private final String name;
		private final Apps apps;
		final List<AppDTO> list = new ArrayList<AppDTO>(20);

		public Filter(final String name, final Apps apps) {
			this.name = name;
			this.apps = apps;
		}

		@Override
		public void each(final String appid) {
			final App app = app(appid);
			if (app.isInCategory(name)) {
				final AppDTO dto =
						new AppDTO(appid,
								"Oh no! I was parsing the name from the store page!");
				list.add(dto);
			}
		}

		private App app(final String appid) throws Error {
			try {
				return apps.app(appid);
			} catch (final NotFound e) {
				throw new Error("cannot happen");
			}
		}
	}
}