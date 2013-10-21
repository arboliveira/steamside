package br.com.arbo.steamside.steam.client.localfiles.appcache.inmemory;

public class NotFound extends Exception {

	public static NotFound appid(final String appid) {
		return new NotFound("appinfo.vdf missing: " + appid);
	}

	private NotFound(final String message) {
		super(message);
	}
}