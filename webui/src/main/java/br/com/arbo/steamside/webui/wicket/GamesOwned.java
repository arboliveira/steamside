package br.com.arbo.steamside.webui.wicket;

public class GamesOwned {

	public GamesOwned(final SharedConfigConsume config) {
		super();
		this.config = config;
	}

	SharedConfigConsume config;

	int gamesOwned() {
		return config.data().apps().count();
	}
}
