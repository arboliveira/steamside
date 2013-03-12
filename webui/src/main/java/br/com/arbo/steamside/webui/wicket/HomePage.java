package br.com.arbo.steamside.webui.wicket;

import org.apache.wicket.markup.html.WebPage;
import org.apache.wicket.markup.html.basic.Label;

import br.com.arbo.steamside.steam.client.localfiles.sharedconfig.Factory_sharedconfig_vdf.FileNotFound_sharedconfig_vdf;

public class HomePage extends WebPage {

	public HomePage() {
		setStatelessHint(true);

		add(new Label("version", getApplication().getFrameworkSettings()
				.getVersion()));

		final SharedConfigConsume config = new SharedConfigConsume();
		try {
			populateNumbers(config);
		} catch (final FileNotFound_sharedconfig_vdf e) {
			// TODO Redirect to "Where is your Steam?" configuration page
			throw e;
		}
	}

	private void populateNumbers(final SharedConfigConsume config) {
		final GamesOwned o = new GamesOwned(config);
		add(new Label("number-of-games", String.valueOf(o.gamesOwned())));
	}
}