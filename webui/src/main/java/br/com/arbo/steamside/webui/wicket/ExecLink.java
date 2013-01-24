/*
 * Created on 05/02/2010
 */
package br.com.arbo.steamside.webui.wicket;

import java.awt.Desktop;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;

import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.extensions.ajax.markup.html.IndicatingAjaxFallbackLink;

final class ExecLink extends IndicatingAjaxFallbackLink<Object> {

	private final String appid;

	public ExecLink(final String id, String appid) {
		super(id);
		this.appid = appid;
	}

	@Override
	public void onClick(final AjaxRequestTarget target) {
		try {
			Desktop.getDesktop().browse(new URI("steam://rungameid/" + appid));
		} catch (IOException e) {
			throw new RuntimeException(e);
		} catch (URISyntaxException e) {
			throw new RuntimeException(e);
		}
	}

}