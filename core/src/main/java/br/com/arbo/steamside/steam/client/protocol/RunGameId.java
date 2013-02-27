package br.com.arbo.steamside.steam.client.protocol;

import java.awt.Desktop;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;

import br.com.arbo.steamside.types.AppId;

public class RunGameId {

	private final AppId appid;

	public RunGameId(final AppId appid) {
		this.appid = appid;
	}

	public void run() {
		try {
			Desktop.getDesktop().browse(
					new URI("steam://rungameid/" + appid.appid));
		} catch (final IOException e) {
			throw new RuntimeException(e);
		} catch (final URISyntaxException e) {
			throw new RuntimeException(e);
		}
	}
}
