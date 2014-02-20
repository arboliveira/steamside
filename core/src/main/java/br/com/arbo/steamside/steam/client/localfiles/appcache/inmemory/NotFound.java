package br.com.arbo.steamside.steam.client.localfiles.appcache.inmemory;

import br.com.arbo.steamside.types.AppId;

@Deprecated
public class NotFound extends RuntimeException {

	public static NotFound appid(final AppId appid) {
		return new NotFound("appinfo.vdf missing: " + appid.appid);
	}

	private NotFound(final String message) {
		super(message);
	}
}