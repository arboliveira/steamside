/*
 * Created on 05/02/2010
 */
package br.com.arbo.steamside.webui.wicket;

import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.extensions.ajax.markup.html.IndicatingAjaxFallbackLink;

import br.com.arbo.steamside.steam.client.protocol.C_rungameid;
import br.com.arbo.steamside.steam.client.protocol.SteamBrowserProtocolLaunch;
import br.com.arbo.steamside.types.AppId;

final class ExecLink extends IndicatingAjaxFallbackLink<Object> {

	private final String appid;

	public ExecLink(final String id, final String appid) {
		super(id);
		this.appid = appid;
	}

	@Override
	public void onClick(final AjaxRequestTarget target) {
		SteamBrowserProtocolLaunch.launch(new C_rungameid(new AppId(appid)));
	}

}