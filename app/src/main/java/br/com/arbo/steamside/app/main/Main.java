package br.com.arbo.steamside.app.main;

import org.picocontainer.MutablePicoContainer;

public class Main {

	private final MutablePicoContainer container;

	public Main(final MutablePicoContainer container) {
		this.container = container;
	}

	public static void main(final String[] args) throws Exception {
		new Main(ContainerFactory.newContainer()).start();
	}

	public void start() {
		container.start();
	}
}
