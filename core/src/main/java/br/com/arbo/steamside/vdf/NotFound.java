package br.com.arbo.steamside.vdf;

public class NotFound extends Exception {

	static NotFound name(final String name) {
		return new NotFound("No sub-region with name: " + name);
	}

	private NotFound(final String message) {
		super(message);
	}
}