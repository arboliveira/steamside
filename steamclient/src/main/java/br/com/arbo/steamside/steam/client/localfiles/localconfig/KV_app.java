package br.com.arbo.steamside.steam.client.localfiles.localconfig;

import java.util.Optional;

import br.com.arbo.steamside.steam.client.types.AppId;
import br.com.arbo.steamside.steam.client.types.LastPlayed;

public interface KV_app {

	AppId appid();

	Optional<LastPlayed> lastPlayed();

}
