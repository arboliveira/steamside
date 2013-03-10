package br.com.arbo.steamside.webui.wicket.search;

import java.util.ArrayList;
import java.util.List;

import br.com.arbo.steamside.steam.store.App;
import br.com.arbo.steamside.steam.store.SearchStore;
import br.com.arbo.steamside.steam.store.SearchStore.SearchResultVisitor;
import br.com.arbo.steamside.webui.appdto.AppCollectionDTO;
import br.com.arbo.steamside.webui.appdto.AppDTO;
import br.com.arbo.steamside.webui.appdto.Size;
import br.com.arbo.steamside.webui.appdto.Visible;

public class Search {

	public static AppCollectionDTO search(final String query) {
		final List<AppDTO> list = new ArrayList<AppDTO>(20);
		SearchStore.search(query, new SearchResultVisitor() {

			@Override
			public void each(final App app) {
				list.add(new AppDTO(
						app.appid, app.name, Size.Large, Visible.True));
			}

		});

		final AppCollectionDTO results = new AppCollectionDTO();
		results.apps = list;
		return results;
	}
}
