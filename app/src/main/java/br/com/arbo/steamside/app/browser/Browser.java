package br.com.arbo.steamside.app.browser;

import java.awt.Desktop;
import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;

public class Browser {

	public static void landing(final int port) {
		final String root = "http://localhost:" + port;
		try {
			final URL url = new URL(
					true ? root :
							favorites(root)
					);
			Desktop.getDesktop().browse(url.toURI());
		} catch (final IOException e) {
			throw new RuntimeException(e);
		} catch (final URISyntaxException e) {
			throw new RuntimeException(e);
		}
	}

	private static String favorites(final String root) {
		return root + "/collection/favorites"
				// TODO Without a final slash, relative apps.json becomes "collection/apps.json" instead of "collection/favorites/apps.json"
				+ "/";
	}
}
