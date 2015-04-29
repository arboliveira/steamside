package br.com.arbo.steamside.steam.client.localfiles.localconfig;

import java.util.Objects;

import br.com.arbo.steamside.steam.client.types.AppId;

public class KV_appticket_Impl implements KV_appticket {

	@Override
	public AppId appid()
	{
		return Objects.requireNonNull(appid);
	}

	AppId appid;

}
