package br.com.arbo.steamside.apps;

public interface Filter {

	void consider(App app) throws Reject;

	public static final class Reject extends Exception {
		//
	}
}