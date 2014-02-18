package br.com.arbo.steamside.steam.client.localfiles.localconfig;

import br.com.arbo.steamside.types.AppId;
import br.com.arbo.steamside.types.LastPlayed;

public interface KV_app {

	AppId appid();

	LastPlayed lastPlayed();

	public interface Visitor {

		void each(KV_app each);
	}
}
