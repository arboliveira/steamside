package br.com.arbo.steamside.app.main;

import br.com.arbo.org.picocontainer.MutablePicoContainerX;

class ExampleRunSteamsideInKidsMode {

	public static void main(final String[] args) {
		final MutablePicoContainerX c = ContainerFactory.newContainer();
		new KidsModeActive().apply(c);
		new Main(c).start();
	}
}
