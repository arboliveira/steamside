package br.com.arbo.steamside.app.main;

import org.picocontainer.MutablePicoContainer;

import br.com.arbo.org.picocontainer.MutablePicoContainerX;

class ExampleRunSteamsideAsDifferentUser {

	public static void main(final String[] args) {
		final MutablePicoContainer c = ContainerFactory.newContainer();
		new DifferentUser().apply(new MutablePicoContainerX(c));
		new Main(c).start();
	}
}
