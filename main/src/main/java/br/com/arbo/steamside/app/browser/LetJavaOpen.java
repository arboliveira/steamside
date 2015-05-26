package br.com.arbo.steamside.app.browser;

import java.awt.Desktop;
import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;

public class LetJavaOpen implements WebBrowser {

	@Override
	public void landing(final int port) {
		final String root = "http://localhost:" + port;
		try {
			final URL url = new URL(root);
			Desktop.getDesktop().browse(url.toURI());
		} catch (final IOException e) {
			throw new RuntimeException(e);
		} catch (final URISyntaxException e) {
			throw new RuntimeException(e);
		}
	}
}
