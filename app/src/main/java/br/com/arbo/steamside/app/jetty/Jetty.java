package br.com.arbo.steamside.app.jetty;

import java.net.URL;

import org.apache.commons.lang3.StringUtils;
import org.apache.wicket.protocol.http.ContextParamWebApplicationFactory;
import org.apache.wicket.protocol.http.WicketFilter;
import org.apache.wicket.protocol.http.WicketServlet;
import org.eclipse.jetty.server.Connector;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.server.bio.SocketConnector;
import org.eclipse.jetty.servlet.DefaultServlet;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;
import org.picocontainer.Startable;

import br.com.arbo.steamside.app.port.Port;
import br.com.arbo.steamside.opersys.username.Username;
import br.com.arbo.steamside.webui.wicket.WicketApplication;

public class Jetty implements Startable {

	private final Port port;

	private final Username username;

	public Jetty(final Port port, final Username username,
			final Callback callback) {
		this.port = port;
		this.username = username;
		this.callback = callback;
	}

	@Override
	public void start() {
		final int portInUse = port.port();

		final Server server = new Server();

		/* Setup server (port, etc.) */

		final SocketConnector connector = new SocketConnector();
		// Set some timeout options to make debugging easier.
		connector.setMaxIdleTime(1000 * 60 * 60);
		connector.setSoLingerTime(-1);
		connector.setPort(portInUse);
		server.setConnectors(new Connector[] { connector });

		/* END Setup server (port, etc.) */

		final ServletContextHandler sch = new ServletContextHandler(
				ServletContextHandler.SESSIONS);

		final ServletHolder sh = new ServletHolder(WicketServlet.class);

		WicketApplication.nextUsername = username;
		final Class<WicketApplication> classWicketApplication = WicketApplication.class;
		sh.setInitParameter(
				ContextParamWebApplicationFactory.APP_CLASS_PARAM,
				classWicketApplication.getName());

		sh.setInitParameter(WicketFilter.FILTER_MAPPING_PARAM, "/*");
		/* Define a variable DEV_MODE and set to false
		 * if wicket should be used in deployment mode
		 */
		if (!DEV_MODE)
			sh.setInitParameter("wicket.configuration", "deployment");

		// Static resources
		final URL resource = classWicketApplication.getClassLoader()
				.getResource(
						parent_of_static(classWicketApplication) + "/static/");
		final String staticPath = resource.toExternalForm();
		final ServletHolder resourceServlet = new ServletHolder(
				DefaultServlet.class);
		resourceServlet.setInitParameter("dirAllowed", "true");
		resourceServlet.setInitParameter("resourceBase", staticPath);
		resourceServlet.setInitParameter("pathInfoOnly", "true");

		sch.addServlet(resourceServlet, "/static/*");
		sch.addServlet(sh, "/*");
		server.setHandler(sch);

		Instructions.starting();
		doStart(server, portInUse);
	}

	private void doStart(final Server server, final int portInUse) {
		try {
			server.start();
			started(portInUse);
			while (System.in.available() == 0)
				Thread.sleep(5000);
			server.stop();
			server.join();
		} catch (final RuntimeException e) {
			throw e;
		} catch (final Exception e) {
			throw new RuntimeException(e);
		}
	}

	private void started(final int portInUse) {
		Instructions.started(portInUse);
		callback.started(portInUse);
	}

	private static String parent_of_static(
			final Class<WicketApplication> classWicketApplication) {
		final String packagename = classWicketApplication.getPackage()
				.getName();
		return StringUtils.replaceChars(packagename, '.', '/');
	}

	private final Callback callback;

	static boolean DEV_MODE = true;

	@Override
	public void stop() {
		// Nothing to do
	}

}
