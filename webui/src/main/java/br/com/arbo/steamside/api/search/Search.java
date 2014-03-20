package br.com.arbo.steamside.api.search;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.jdt.annotation.NonNull;

import br.com.arbo.steamside.api.app.AppDTO;
import br.com.arbo.steamside.steam.store.App;
import br.com.arbo.steamside.steam.store.SearchStore;
import br.com.arbo.steamside.steam.store.SearchStore.SearchResultVisitor;

class Search {

	static List<AppDTO> search(@NonNull final String query) {
		final List<AppDTO> list = new ArrayList<AppDTO>(20);
		SearchStore.search(query, new SearchResultVisitor() {

			@Override
			public void each(final App app) {
				list.add(new AppDTO(
						app.appid, app.name));
			}

		});
		return list;
	}
}
