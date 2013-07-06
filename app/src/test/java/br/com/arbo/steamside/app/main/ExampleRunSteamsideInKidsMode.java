package br.com.arbo.steamside.app.main;

import org.picocontainer.MutablePicoContainer;

import br.com.arbo.org.picocontainer.MutablePicoContainerX;

class ExampleRunSteamsideInKidsMode {

	public static void main(final String[] args) {
		final MutablePicoContainer c = ContainerFactory.newContainer();
		new KidsModeActive().apply(new MutablePicoContainerX(c));
		new Main(c).start();
	}
}
