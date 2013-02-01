package br.com.arbo.steamside.webui.wicket;

import org.apache.wicket.markup.html.WebPage;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.link.Link;
import org.apache.wicket.request.mapper.parameter.PageParameters;

import br.com.arbo.steamside.vdf.SharedconfigVdfLocation.SharedconfigVdfMissing;

public class HomePage extends WebPage {

	public HomePage(final PageParameters parameters) {
		super(parameters);

		add(new Label("version", getApplication().getFrameworkSettings()
				.getVersion()));

		final SharedConfigConsume config = new SharedConfigConsume();
		try {
			populateContinue();
			populateNumbers(config);
		} catch (final SharedconfigVdfMissing e) {
			// TODO Redirect to "Where is your Steam?" configuration page
			throw e;
		}

		// TODO Add your page's components here

	}

	private void populateNumbers(final SharedConfigConsume config) {
		final GamesOwned o = new GamesOwned(config);
		add(new Label("number-of-games", String.valueOf(o.gamesOwned())));
	}

	private void populateContinue() {
		final HomePage item = this;

		final String appid = "72850";
		final String caption = "Skyrim";
		final Link< ? > externalLink = newLink("link", appid);
		externalLink.add(new Label("caption", caption));
		item.add(externalLink);
	}

	private static Link< ? > newLink(final String id, final String appid) {
		return new ExecLink(id, appid);
	}
}