package br.com.arbo.steamside.steam.client.localfiles.localconfig;

import br.com.arbo.steamside.types.AppId;
import br.com.arbo.steamside.types.LastPlayed;

public class KV_app_Impl implements KV_app {

	AppId appid;
	LastPlayed lastPlayed;

	@Override
	public AppId appid() {
		return appid;
	}

	@Override
	public LastPlayed lastPlayed() {
		return lastPlayed;
	}

}
