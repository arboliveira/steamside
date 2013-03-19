package br.com.arbo.steamside.webui.wicket.steamclient;

import org.apache.wicket.request.mapper.parameter.PageParameters;

import br.com.arbo.org.apache.wicket.markup.html.pages.EmptyPage;
import br.com.arbo.steamside.steam.client.protocol.C_open_main;
import br.com.arbo.steamside.steam.client.protocol.SteamBrowserProtocol;
import br.com.arbo.steamside.webui.wicket.WicketApplication;

public class SteamClientPage extends EmptyPage {

	public static final String PARAM_command = "command";
	public static final String PARAM_argument = "argument";

	public SteamClientPage(final PageParameters p) {
		super(p);
		setStatelessHint(true);
		final String command = p.get(PARAM_command).toString();
		final String argument = p.get(PARAM_argument).toString();
		if (equalsCommand("open", command) && equalsCommand("main", argument))
			c_open_main();
	}

	private static void c_open_main() {
		final SteamBrowserProtocol steam =
				WicketApplication.getContainer()
						.getComponent(SteamBrowserProtocol.class);
		steam.launch(new C_open_main());
	}

	private static boolean equalsCommand(final String string,
			final String command) {
		return string.equals(command);
	}

}
