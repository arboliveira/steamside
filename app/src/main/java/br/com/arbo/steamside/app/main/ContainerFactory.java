package br.com.arbo.steamside.app.main;

import static org.picocontainer.Characteristics.CACHE;

import org.picocontainer.MutablePicoContainer;
import org.picocontainer.PicoBuilder;

import br.com.arbo.steamside.app.jetty.Callback;
import br.com.arbo.steamside.app.jetty.Jetty;

class ContainerFactory {

	private final Callback callbackJetty;

	ContainerFactory(final Callback callbackJetty) {
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
				.addComponent(Callback.class, this.callbackJetty);

		return container;
	}
}
