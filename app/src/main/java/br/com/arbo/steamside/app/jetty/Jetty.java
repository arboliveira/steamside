package br.com.arbo.steamside.app.jetty;

import java.net.BindException;
import java.net.URL;

import javax.inject.Inject;

import org.eclipse.jetty.server.Connector;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.server.bio.SocketConnector;
import org.eclipse.jetty.servlet.DefaultServlet;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.servlet.DispatcherServlet;

import br.com.arbo.steamside.app.port.Port;
import br.com.arbo.steamside.app.port.PortAlreadyInUse;
import br.com.arbo.steamside.exit.Exit;
import br.com.arbo.steamside.opersys.username.User;

public class Jetty implements LocalWebserver {

	@Override
	public void launch(final int port) throws PortAlreadyInUse {
		final int portToUse = port;
		final Port portInUse = new Port(port);

		/* Setup server (port, etc.) */

		final SocketConnector connector = new SocketConnector();
		// Set some timeout options to make debugging easier.
		connector.setMaxIdleTime(1000 * 60 * 60);
		connector.setSoLingerTime(-1);
		connector.setPort(portToUse);
		server.setConnectors(new Connector[] { connector });

		/* END Setup server (port, etc.) */

		final ServletContextHandler context = new ServletContextHandler(
				ServletContextHandler.SESSIONS);

		// REST api
		addServlet_api(context);

		// Static resources
		final String root_of_resources = root_of_resources();
		addServlet_html(
				root_of_resources, context);
		server.setHandler(context);

		Instructions.starting();
		doStart();
		xtWaitForUserToPressEnterAndExit();
		Instructions.started(portInUse);
	}

	@Override
	public void stop() {
		try {
			server.stop();
			server.join();
		}
		catch (final RuntimeException e) {
			throw e;
		}
		catch (final Exception e) {
			throw new RuntimeException(e);
		}
	}

	private void addServlet_html(
			final String root_of_resources, final ServletContextHandler sch) {
		sch.setWelcomeFiles(new String[] { "Steamside.html" });

		final String fromRoot = "";
		final ServletHolder servlet = newServlet(
				root_of_resources, fromRoot);

		// don't use "/*" or Jetty will not process file extension mappings
		sch.addServlet(servlet, "/");
	}

	private ServletHolder newServlet(
			final String root_of_resources, final String suffix) {
		final String path = toExternalForm(
				root_of_resources, suffix);
		final ServletHolder servlet = new ServletHolder(
				DefaultServlet.class);
		servlet.setInitParameter("resourceBase", path);
		return servlet;
	}

	private String toExternalForm(
			final String root_of_resources, final String suffix) {
		final URL resource = this.getClass().getClassLoader()
				.getResource(
						root_of_resources + "/" + suffix);
		return resource.toExternalForm();
	}

	private void addServlet_api(
			final ServletContextHandler context) {
		@SuppressWarnings("resource")
		final WebApplicationContext springContext = webApplicationContextBuilder
				.newSpringContext();
		final ServletHolder servletHolder =
				new ServletHolder(new DispatcherServlet(springContext));
		context.addServlet(servletHolder,
				String.format(
						"/%s/*",
						br.com.arbo.steamside.mapping.Api.api));
	}

	interface ServletHolderSetup {

		void setup(ServletHolder servlet);
	}

	private void doStart() {
		try {
			server.start();
		}
		catch (final BindException e) {
			throw new PortAlreadyInUse(e);
		}
		catch (final RuntimeException e) {
			throw e;
		}
		catch (final Exception e) {
			throw new RuntimeException(e);
		}
	}

	private void xtWaitForUserToPressEnterAndExit() {
		final Thread thread = new Thread(
				new WaitForUserToPressEnterThenExit(exit),
				"Press Enter to exit SteamSide");
		thread.setDaemon(true);
		thread.start();
	}

	private static String root_of_resources() {
		return "br/com/arbo/steamside/webui";
	}

	static boolean DEV_MODE = true;

	@Inject
	public Jetty(
			User username,
			Exit exit,
			WebApplicationContextTweak tweak) {
		this.webApplicationContextBuilder = new WebApplicationContextBuilder(
				username, exit, tweak);
		this.exit = exit;
		this.server = new Server();
	}

	private final Exit exit;
	private final Server server;

	private final WebApplicationContextBuilder webApplicationContextBuilder;

}
