package br.com.arbo.steamside.app.main;

import static org.picocontainer.Characteristics.CACHE;

import org.picocontainer.MutablePicoContainer;
import org.picocontainer.PicoBuilder;

import br.com.arbo.steamside.app.browser.Browser;
import br.com.arbo.steamside.app.browser.WebBrowser;
import br.com.arbo.steamside.app.instance.DetectSteamside;
import br.com.arbo.steamside.app.instance.DetectSteamsideFromURL;
import br.com.arbo.steamside.app.instance.RangeSize;
import br.com.arbo.steamside.app.instance.SingleInstancePerUser;
import br.com.arbo.steamside.app.jetty.Jetty;
import br.com.arbo.steamside.app.jetty.LocalWebserver;
import br.com.arbo.steamside.opersys.username.Username;
import br.com.arbo.steamside.opersys.username.UsernameFromJava;

class ContainerFactory {

	static MutablePicoContainer newContainer() {
		final MutablePicoContainer container = newContainerEmpty();
		container.setName("Launch");
		container
				.addComponent(SingleInstancePerUser.class)
				.addComponent(DetectSteamside.class,
						DetectSteamsideFromURL.class)
				.addComponent(new RangeSize(10))
				.addComponent(LocalWebserver.class, Jetty.class)
				.addComponent(WebBrowser.class, Browser.class)
				.addComponent(Username.class, UsernameFromJava.class)
		//
		;

		return container;
	}

	public static MutablePicoContainer newContainerEmpty() {
		final MutablePicoContainer container = new PicoBuilder()
				.withCaching()
				.withLifecycle()
				.build();
		container.change(CACHE);
		return container;
	}

	static void replaceComponent(final MutablePicoContainer container,
			final Object componentKey,
			final Object componentImplementationOrInstance) {
		container.removeComponent(componentKey);
		container.addComponent(
				componentKey,
				componentImplementationOrInstance);
	}

}
