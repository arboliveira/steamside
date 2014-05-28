package br.com.arbo.steamside.steam.client.protocol;

import br.com.arbo.steamside.steam.client.types.AppId;

public class C_rungameid implements Command {

	private final AppId appid;

	public C_rungameid(final AppId appid) {
		this.appid = appid;
	}

	@Override
	public String command() {
		return "rungameid/" + appid.appid;
	}
}
