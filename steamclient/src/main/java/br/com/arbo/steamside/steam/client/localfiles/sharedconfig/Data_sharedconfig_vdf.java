package br.com.arbo.steamside.steam.client.localfiles.sharedconfig;

import java.util.Optional;
import java.util.stream.Stream;

import br.com.arbo.steamside.steam.client.types.AppId;

public interface Data_sharedconfig_vdf
{

	Stream<AppId> everyAppId();

	Optional<Entry_app> get(AppId appid);

}
