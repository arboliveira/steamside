package br.com.arbo.steamside.app.main;

import br.com.arbo.org.picocontainer.MutablePicoContainerX;

public class NoCollections implements Part {

	@Override
	public void apply(final MutablePicoContainerX c) {
		// TODO Auto-generated method stub

	}

	public static Part on() {
		return new NoCollections();
	}

}
