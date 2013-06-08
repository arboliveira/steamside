package br.com.arbo.steamside.webui.wicket.session.json;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

class MavenBuild {

	String readVersion() {
		final Properties p = new Properties();
		load(p);
		return p.getProperty("maven.build.timestamp");
	}

	private void load(final Properties p) {
		final InputStream inStream =
				this.getClass().getResourceAsStream(
						"/MavenBuild.properties");
		try {
			try {
				p.load(inStream);
			} finally {
				inStream.close();
			}
		} catch (final IOException e) {
			throw new RuntimeException(e);
		}
	}
}
