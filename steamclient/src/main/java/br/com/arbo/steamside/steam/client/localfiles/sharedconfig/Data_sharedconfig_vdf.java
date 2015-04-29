package br.com.arbo.steamside.steam.client.localfiles.sharedconfig;

import java.util.Optional;

import br.com.arbo.steamside.steam.client.types.AppId;

public interface Data_sharedconfig_vdf {

	R_apps apps();

	Optional<Entry_app> get(AppId appid);

}
