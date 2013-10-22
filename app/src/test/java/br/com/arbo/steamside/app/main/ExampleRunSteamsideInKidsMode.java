package br.com.arbo.steamside.app.main;

import br.com.arbo.steamside.app.injection.Container;

class ExampleRunSteamsideInKidsMode {

	public static void main(final String[] args) {
		final Container c = ContainerFactory.newContainer();
		new KidsModeActive().apply(c);
		new Main(c).start();
	}
}
