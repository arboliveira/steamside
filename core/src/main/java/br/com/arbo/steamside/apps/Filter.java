package br.com.arbo.steamside.apps;

import java.util.function.Predicate;

import br.com.arbo.steamside.steam.client.apps.App;

public interface Filter extends Predicate<App> {

	@Override
	default boolean test(App app) {
		try {
			consider(app);
			return true;
		} catch (final Reject e) {
			return false;
		}
	}

	void consider(App app) throws Reject;

	public static final class Reject extends Exception {
		//
	}
}