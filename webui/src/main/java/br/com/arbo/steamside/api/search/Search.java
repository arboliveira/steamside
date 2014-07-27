package br.com.arbo.steamside.api.search;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.jdt.annotation.NonNull;

import br.com.arbo.steamside.api.app.AppApi;
import br.com.arbo.steamside.steam.store.App;
import br.com.arbo.steamside.steam.store.SearchStore;

class Search {

	static List<AppApi> search(@NonNull final String query)
	{
		final List<AppApi> list = new ArrayList<AppApi>(20);
		SearchStore.search(
				query, app -> list.add(newAppApiSearch(app)));
		return list;
	}

	private static AppApi newAppApiSearch(App app)
	{
		return new AppApiSearch(app);
	}
}
