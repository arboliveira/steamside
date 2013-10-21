package br.com.arbo.steamside.app.main;

import br.com.arbo.org.picocontainer.MutablePicoContainerX;

class ExampleRunSteamsideAsDifferentUser {

	public static void main(final String[] args) {
		final MutablePicoContainerX c = ContainerFactory.newContainer();
		new DifferentUser().apply(c);
		new Main(c).start();
	}
}
