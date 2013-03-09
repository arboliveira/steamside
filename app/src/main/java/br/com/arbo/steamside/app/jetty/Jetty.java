package br.com.arbo.steamside.app.jetty;

import java.net.BindException;
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

import br.com.arbo.steamside.app.port.Port;
import br.com.arbo.steamside.app.port.PortAlreadyInUse;
import br.com.arbo.steamside.opersys.username.Username;
import br.com.arbo.steamside.webui.wicket.WicketApplication;

public class Jetty implements LocalWebserver {

	private final Username username;

	public Jetty(final Username username) {
		this.username = username;
	}

	@Override
	public void launch(final int port) throws PortAlreadyInUse {
		final int portToUse = port;
		final Port portInUse = new Port(port);

		final Server server = new Server();

		/* Setup server (port, etc.) */

		final SocketConnector connector = new SocketConnector();
		// Set some timeout options to make debugging easier.
		connector.setMaxIdleTime(1000 * 60 * 60);
		connector.setSoLingerTime(-1);
		connector.setPort(portToUse);
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
		doStart(server);
		xtWaitForUserToPressEnterAndStop(server);
		Instructions.started(portInUse);
	}

	private static void xtWaitForUserToPressEnterAndStop(final Server server) {
		new Thread(
				new WaitForUserToPressEnterAndStop(server),
				"Press Enter to end SteamSide").start();
	}

	private static void doStart(final Server server) {
		try {
			server.start();
		} catch (final BindException e) {
			throw new PortAlreadyInUse(e);
		} catch (final RuntimeException e) {
			throw e;
		} catch (final Exception e) {
			throw new RuntimeException(e);
		}
	}

	static class WaitForUserToPressEnterAndStop implements Runnable {

		private final Server server;

		WaitForUserToPressEnterAndStop(final Server server) {
			this.server = server;
		}

		@Override
		public void run() {
			try {
				System.in.read();
				server.stop();
				server.join();
			} catch (final RuntimeException e) {
				throw e;
			} catch (final Exception e) {
				throw new RuntimeException(e);
			}
		}
	}

	private static String parent_of_static(
			final Class<WicketApplication> classWicketApplication) {
		final String packagename = classWicketApplication.getPackage()
				.getName();
		return StringUtils.replaceChars(packagename, '.', '/');
	}

	static boolean DEV_MODE = true;

}
