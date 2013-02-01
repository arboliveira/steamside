package br.com.arbo.steamside.app.jetty;

import java.awt.Desktop;
import java.net.URL;

import org.apache.wicket.protocol.http.ContextParamWebApplicationFactory;
import org.apache.wicket.protocol.http.WicketFilter;
import org.apache.wicket.protocol.http.WicketServlet;
import org.eclipse.jetty.server.Connector;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.server.bio.SocketConnector;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;

import br.com.arbo.steamside.webui.wicket.WicketApplication;

public class Jetty {

	private static final int PORT = 42224;

	static boolean DEV_MODE = true;

	public static URL start() throws Exception {
		final Server server = new Server();

		/* Setup server (port, etc.) */

		final SocketConnector connector = new SocketConnector();
		// Set some timeout options to make debugging easier.
		connector.setMaxIdleTime(1000 * 60 * 60);
		connector.setSoLingerTime(-1);
		connector.setPort(PORT);
		server.setConnectors(new Connector[] { connector });

		/* END Setup server (port, etc.) */

		ServletContextHandler sch = new ServletContextHandler(
				ServletContextHandler.SESSIONS);
		ServletHolder sh = new ServletHolder(WicketServlet.class);
		sh.setInitParameter(ContextParamWebApplicationFactory.APP_CLASS_PARAM,
				WicketApplication.class.getName());
		sh.setInitParameter(WicketFilter.FILTER_MAPPING_PARAM, "/*");
		/* Define a variable DEV_MODE and set to false
		 * if wicket should be used in deployment mode
		 */
		if (!DEV_MODE) {
			sh.setInitParameter("wicket.configuration", "deployment");
		}
		sch.addServlet(sh, "/*");
		server.setHandler(sch);

		Instructions.starting();
		server.start();
		Instructions.started(PORT);

		final URL url = new URL("http://localhost:" + PORT);

		// TODO Callback
		Desktop.getDesktop().browse(url.toURI());

		while (System.in.available() == 0)
			Thread.sleep(5000);
		server.stop();
		server.join();

		return url;
	}
}
