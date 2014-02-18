package br.com.arbo.steamside.steam.client.localfiles.localconfig;

import org.eclipse.jdt.annotation.Nullable;

import br.com.arbo.steamside.types.AppId;

public interface Data_localconfig_vdf {

	KV_apps apps();

	@Nullable
	KV_app get(AppId appid);

}
