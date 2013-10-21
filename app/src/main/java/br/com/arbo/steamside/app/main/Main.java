package br.com.arbo.steamside.app.main;

import br.com.arbo.org.picocontainer.MutablePicoContainerX;

public class Main {

	private final MutablePicoContainerX container;

	public Main(final MutablePicoContainerX container) {
		this.container = container;
	}

	public static void main(final String[] args) throws Exception {
		new Main(ContainerFactory.newContainer()).start();
	}

	public void start() {
		container.start();
	}
}
