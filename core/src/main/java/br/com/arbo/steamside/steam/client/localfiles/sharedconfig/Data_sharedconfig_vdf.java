package br.com.arbo.steamside.steam.client.localfiles.sharedconfig;

import br.com.arbo.steamside.types.AppId;

public interface Data_sharedconfig_vdf {

	R_apps apps();

	Entry_app get(AppId appid);

}
