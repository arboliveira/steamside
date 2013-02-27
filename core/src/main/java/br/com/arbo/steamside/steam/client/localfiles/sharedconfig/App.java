package br.com.arbo.steamside.steam.client.localfiles.sharedconfig;

import java.util.Collection;

import br.com.arbo.steamside.types.AppId;
import br.com.arbo.steamside.types.Category;

public class App {

	public boolean isInCategory(final Category category) {
		return categories != null && categories.contains(category.category);
	}

	public AppId appid() {
		return appid;
	}

	public AppId appid;
	String lastPlayed;
	String cloudEnabled;
	public Collection<String> categories;

	public void onKeyValue(final String k, final String v) {
		if ("LastPlayed".equalsIgnoreCase(k))
			lastPlayed = v;
		else if ("CloudEnabled".equalsIgnoreCase(k))
			cloudEnabled = v;
	}

}
