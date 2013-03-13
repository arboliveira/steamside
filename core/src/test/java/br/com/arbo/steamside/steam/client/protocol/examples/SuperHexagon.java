package br.com.arbo.steamside.steam.client.protocol.examples;

import br.com.arbo.steamside.opersys.username.FromJava;
import br.com.arbo.steamside.steam.client.protocol.C_rungameid;
import br.com.arbo.steamside.steam.client.protocol.SteamBrowserProtocol;
import br.com.arbo.steamside.types.AppId;

public class SuperHexagon {

	public static void main(final String[] args) {
		final C_rungameid command = new C_rungameid(new AppId("221640"));
		final SteamBrowserProtocol steam =
				new SteamBrowserProtocol(new FromJava());
		steam.launch(command);
	}
}
