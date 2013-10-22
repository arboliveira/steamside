package br.com.arbo.steamside.app.main;

import br.com.arbo.steamside.app.injection.Container;

class ExampleRunSteamsideAsDifferentUser {

	public static void main(final String[] args) {
		final Container c = ContainerFactory.newContainer();
		new DifferentUser().apply(c);
		new Main(c).start();
	}
}
