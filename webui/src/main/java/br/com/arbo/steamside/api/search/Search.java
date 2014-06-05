package br.com.arbo.steamside.api.search;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.eclipse.jdt.annotation.NonNull;

import br.com.arbo.steamside.api.app.AppDTO;
import br.com.arbo.steamside.steam.store.App;
import br.com.arbo.steamside.steam.store.SearchStore;

class Search {

	static List<AppDTO> search(@NonNull final String query)
	{
		final List<AppDTO> list = new ArrayList<AppDTO>(20);
		SearchStore.search(
				query, app -> list.add(newAppDTO(app)));
		return list;
	}

	private static AppDTO newAppDTO(App app)
	{
		return new AppDTO(
				app.appid, app.name, Collections.emptyList(), false);
	}
}
