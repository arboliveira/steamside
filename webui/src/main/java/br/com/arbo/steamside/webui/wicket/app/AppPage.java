package br.com.arbo.steamside.webui.wicket.app;

import org.apache.wicket.request.mapper.parameter.PageParameters;

import br.com.arbo.org.apache.wicket.markup.html.pages.EmptyPage;
import br.com.arbo.steamside.steam.client.protocol.C_rungameid;
import br.com.arbo.steamside.steam.client.protocol.SteamBrowserProtocol;
import br.com.arbo.steamside.types.AppId;

public class AppPage extends EmptyPage {

	public static final String PARAM_command = "command";
	public static final String PARAM_appid = "appid";

	public AppPage(final PageParameters p) {
		super(p);
		setStatelessHint(true);
		final AppId appid =
				new AppId(
						p.get(PARAM_appid).toString()
				);
		final String command = p.get(PARAM_command).toString();
		if (equalsCommand("run", command))
			SteamBrowserProtocol.launch(new C_rungameid(appid));
	}

	private static boolean equalsCommand(final String string,
			final String command) {
		return string.equals(command);
	}
}
