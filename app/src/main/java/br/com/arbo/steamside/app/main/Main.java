package br.com.arbo.steamside.app.main;

import br.com.arbo.steamside.app.injection.Container;

public class Main {

	private final Container container;

	public Main(final Container container) {
		this.container = container;
	}

	public static void main(final String[] args) throws Exception {
		new Main(ContainerFactory.newContainer()).start();
	}

	public void start() {
		container.start();
	}
}
