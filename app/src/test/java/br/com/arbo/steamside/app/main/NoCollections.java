package br.com.arbo.steamside.app.main;

import br.com.arbo.steamside.app.injection.Container;

public class NoCollections implements Part {

	@Override
	public void apply(final Container c) {
		// TODO Auto-generated method stub

	}

	public static Part on() {
		return new NoCollections();
	}

}
