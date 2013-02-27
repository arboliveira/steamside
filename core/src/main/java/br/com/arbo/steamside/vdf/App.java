package br.com.arbo.steamside.vdf;

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

	AppId appid;
	String lastPlayed;
	String cloudEnabled;
	Collection<String> categories;

}