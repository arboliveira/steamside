package br.com.arbo.steamside.collection;

import br.com.arbo.steamside.apps.App;

public interface Filter {

	void consider(App app) throws Reject;

	public static final class Reject extends Exception {
		//
	}
}