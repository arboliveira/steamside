package br.com.arbo.steamside.webui.wicket;

public class GamesOwned {

	public GamesOwned(SharedConfigConsume config) {
		super();
		this.config = config;
	}

	SharedConfigConsume config;

	int gamesOwned() {
		return config.number();
	}
}
