package br.com.arbo.steamside.steam.client.localfiles.localconfig;

import org.eclipse.jdt.annotation.NonNull;

import br.com.arbo.steamside.types.AppId;
import br.com.arbo.steamside.types.LastPlayed;

public class KV_app_Impl implements KV_app {

	AppId appid;
	LastPlayed lastPlayed;

	@Override
	@NonNull
	public AppId appid() {
		AppId _appid = appid;
		if (_appid == null) throw new NullPointerException();
		return _appid;
	}

	@Override
	public LastPlayed lastPlayed() {
		return lastPlayed;
	}

}
