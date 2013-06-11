package br.com.arbo.steamside.app.jetty;

import java.io.IOException;
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
import org.springframework.web.servlet.DispatcherServlet;

import br.com.arbo.org.picocontainer.MutablePicoContainerX;
import br.com.arbo.steamside.app.port.Port;
import br.com.arbo.steamside.app.port.PortAlreadyInUse;
import br.com.arbo.steamside.exit.Exit;
import br.com.arbo.steamside.kids.KidsMode;
import br.com.arbo.steamside.opersys.username.User;
import br.com.arbo.steamside.spring.SteamsideApplicationContext;
import br.com.arbo.steamside.webui.wicket.ContainerFactory;
import br.com.arbo.steamside.webui.wicket.WicketApplication;

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

		final MutablePicoContainerX container = newContainer();
		WicketApplication.nextContainer = container;

		final Class<WicketApplication> classWicketApplication = WicketApplication.class;

		// REST api
		addServlet_api(context, container);

		// Static resources
		final String root_of_resources = root_of_resources(classWicketApplication);
		addServlet_html(
				classWicketApplication, root_of_resources, context);
		addServlet_Wicket(context, classWicketApplication);
		server.setHandler(context);

		Instructions.starting();
		doStart();
		xtWaitForUserToPressEnterAndExit();
		Instructions.started(portInUse);
	}

	private MutablePicoContainerX newContainer() {
		final MutablePicoContainerX cx = ContainerFactory.newContainer();
		cx.replaceComponent(KidsMode.class, this.kidsmode);
		cx.replaceComponent(User.class, this.username);
		cx.replaceComponent(Exit.class, this.exit);
		return cx;
	}

	private static void addServlet_api(
			final ServletContextHandler context,
			final MutablePicoContainerX container) {
		final ServletHolder servletHolder =
				new ServletHolder(
						new DispatcherServlet(
								new SteamsideApplicationContext(
										container)));
		context.addServlet(servletHolder,
				String.format(
						"/%s/*",
						br.com.arbo.steamside.mapping.Api.api));
	}

	private static void addServlet_Wicket(final ServletContextHandler sch,
			final Class<WicketApplication> classWicketApplication) {
		final ServletHolder sh = new ServletHolder(WicketServlet.class);
		sh.setInitParameter(
				ContextParamWebApplicationFactory.APP_CLASS_PARAM,
				classWicketApplication.getName());
		sh.setInitParameter(WicketFilter.FILTER_MAPPING_PARAM, "/*");
		/* Define a variable DEV_MODE and set to false
		 * if wicket should be used in deployment mode
		 */
		if (!DEV_MODE)
			sh.setInitParameter("wicket.configuration", "deployment");

		// don't use "/*" or Jetty will not process file extension mappings
		sch.addServlet(sh, "/");
	}

	private static void addServlet_html(
			final Class<WicketApplication> classWicketApplication,
			final String root_of_resources, final ServletContextHandler sch) {
		final String fromRoot = "";
		final ServletHolder servlet = newServlet(classWicketApplication,
				root_of_resources, fromRoot);
		addServlet(sch, servlet, "*.html", "*.css", "*.js", "*.gif", "*.png");
	}

	private static void addServlet(final ServletContextHandler sch,
			final ServletHolder servlet, final String... pathSpecs) {
		for (final String pathSpec : pathSpecs)
			sch.addServlet(servlet, pathSpec);
	}

	private static ServletHolder newServlet(
			final Class<WicketApplication> classWicketApplication,
			final String root_of_resources, final String suffix) {
		final String path = toExternalForm(classWicketApplication,
				root_of_resources, suffix);
		final ServletHolder servlet = new ServletHolder(
				DefaultServlet.class);
		servlet.setInitParameter("resourceBase", path);
		return servlet;
	}

	interface ServletHolderSetup {

		void setup(ServletHolder servlet);
	}

	private static String toExternalForm(
			final Class<WicketApplication> classWicketApplication,
			final String root_of_resources, final String suffix) {
		final URL resource = classWicketApplication.getClassLoader()
				.getResource(
						root_of_resources + "/" + suffix);
		return resource.toExternalForm();
	}

	@Override
	public void stop() {
		try {
			server.stop();
			server.join();
		} catch (final RuntimeException e) {
			throw e;
		} catch (final Exception e) {
			throw new RuntimeException(e);
		}
	}

	private void doStart() {
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

	private void xtWaitForUserToPressEnterAndExit() {
		class WaitForUserToPressEnterAndExit implements Runnable {

			@Override
			public void run() {
				waitForUserToPressEnterAndExit();
			}
		}
		final Thread thread = new Thread(
				new WaitForUserToPressEnterAndExit(),
				"Press Enter to exit SteamSide");
		thread.setDaemon(true);
		thread.start();
	}

	void waitForUserToPressEnterAndExit() {
		waitForUserToPressEnter();
		exit.exit();
	}

	private static void waitForUserToPressEnter() {
		try {
			System.in.read();
		} catch (final IOException e) {
			throw new RuntimeException(e);
		}
	}

	private static String root_of_resources(
			final Class<WicketApplication> classWicketApplication) {
		final String packagename = classWicketApplication.getPackage()
				.getName();
		return StringUtils.replaceChars(packagename, '.', '/');
	}

	static boolean DEV_MODE = true;

	public Jetty(final User username, final KidsMode kidsmode, final Exit exit) {
		this.username = username;
		this.kidsmode = kidsmode;
		this.exit = exit;
		this.server = new Server();
	}

	private final User username;
	private final KidsMode kidsmode;
	private final Exit exit;
	private final Server server;

}
