package br.com.arbo.steamside.apps;

public class NotFound extends RuntimeException {

	public static NotFound appid(final String appid) {
		return new NotFound("No app with id: " + appid);
	}

	private NotFound(final String message) {
		super(message);
	}
}