package br.com.arbo.steamside.steam.client.localfiles.localconfig;

import java.util.Objects;
import java.util.Optional;

import br.com.arbo.steamside.steam.client.types.AppId;
import br.com.arbo.steamside.steam.client.types.LastPlayed;

public class KV_app_Impl implements KV_app {

	@Override
	public AppId appid()
	{
		return Objects.requireNonNull(appid);
	}

	@Override
	public Optional<LastPlayed> lastPlayed()
	{
		return Optional.ofNullable(lastPlayed);
	}

	AppId appid;

	LastPlayed lastPlayed;

}
