package br.com.arbo.steamside.app.main;

import static org.picocontainer.Characteristics.CACHE;

import org.picocontainer.MutablePicoContainer;
import org.picocontainer.PicoBuilder;

import br.com.arbo.steamside.app.jetty.Callback;
import br.com.arbo.steamside.app.jetty.Jetty;
import br.com.arbo.steamside.app.port.FreePortLocator;
import br.com.arbo.steamside.app.port.Port;
import br.com.arbo.steamside.opersys.username.Username;

class ContainerFactory {

	private final Username username;
	private final Callback callbackJetty;

	ContainerFactory(final Username username, final Callback callbackJetty) {
		this.username = username;
		this.callbackJetty = callbackJetty;
	}

	MutablePicoContainer newContainer() {
		final MutablePicoContainer container = new PicoBuilder()
				.withCaching()
				.withLifecycle()
				.build();

		container.setName("Launch");
		container.change(CACHE);
		container
				.addComponent(Jetty.class)
				.addComponent(Username.class, username)
				.addComponent(Port.class, FreePortLocator.class)
				.addComponent(Callback.class, this.callbackJetty)
		//
		;

		return container;
	}
}
