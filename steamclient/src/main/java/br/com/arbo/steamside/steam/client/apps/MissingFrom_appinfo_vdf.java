package br.com.arbo.steamside.steam.client.apps;

import br.com.arbo.steamside.steam.client.types.AppId;

public class MissingFrom_appinfo_vdf extends RuntimeException {

	public static MissingFrom_appinfo_vdf appid(final AppId appid) {
		return new MissingFrom_appinfo_vdf(
				"appinfo.vdf missing: " + appid.appid);
	}

	private MissingFrom_appinfo_vdf(final String message) {
		super(message);
	}
}
