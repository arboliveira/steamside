package br.com.arbo.steamside.protocol;

import java.awt.Desktop;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;

public class RunGameId {

	private final String appid;

	public RunGameId(final String appid) {
		this.appid = appid;
	}

	public void run() {
		try {
			Desktop.getDesktop().browse(new URI("steam://rungameid/" + appid));
		} catch (final IOException e) {
			throw new RuntimeException(e);
		} catch (final URISyntaxException e) {
			throw new RuntimeException(e);
		}
	}
}
