package br.com.arbo.steamside.webui.wicket;

import org.apache.wicket.markup.html.WebPage;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.link.Link;
import org.apache.wicket.request.mapper.parameter.PageParameters;

public class HomePage extends WebPage {

	private static final long serialVersionUID = 1L;

	public HomePage(final PageParameters parameters) {
		super(parameters);

		add(new Label("version", getApplication().getFrameworkSettings()
				.getVersion()));

		HomePage item = this;

		final String appid = "72850";
		final String caption = "Skyrim";
		final Link< ? > externalLink = newLink("link", appid);
		externalLink.add(new Label("caption", caption));
		item.add(externalLink);

		// TODO Add your page's components here

	}

	private static Link< ? > newLink(final String id, final String appid) {
		return new ExecLink(id, appid);
	}
}