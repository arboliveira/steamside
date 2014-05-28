package br.com.arbo.steamside.apps;

import br.com.arbo.steamside.steam.client.apps.App;
import br.com.arbo.steamside.steam.client.types.SteamCategory;

public class FilterCategory implements Filter {

	private final SteamCategory category;

	public FilterCategory(final SteamCategory category) {
		this.category = category;
	}

	@Override
	public void consider(final App app) throws Reject {
		if (!app.isInCategory(category)) throw new Reject();
	}

}