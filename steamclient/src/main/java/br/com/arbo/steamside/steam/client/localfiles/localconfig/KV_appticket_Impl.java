package br.com.arbo.steamside.steam.client.localfiles.localconfig;

import org.eclipse.jdt.annotation.NonNull;

import br.com.arbo.steamside.steam.client.types.AppId;

public class KV_appticket_Impl implements KV_appticket {

	@Override
	@NonNull
	public AppId appid()
	{
		AppId _appid = appid;
		if (_appid == null) throw new NullPointerException();
		return _appid;
	}

	AppId appid;

}
