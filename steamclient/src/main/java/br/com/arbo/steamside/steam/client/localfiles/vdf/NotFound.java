package br.com.arbo.steamside.steam.client.localfiles.vdf;

public class NotFound extends Exception {

	public static NotFound name(final String name) {
		return new NotFound("No sub-region with name: " + name);
	}

	private NotFound(final String message) {
		super(message);
	}
}