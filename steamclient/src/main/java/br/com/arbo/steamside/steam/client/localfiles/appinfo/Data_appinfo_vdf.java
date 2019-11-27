package br.com.arbo.steamside.steam.client.localfiles.appinfo;

import java.util.Optional;
import java.util.stream.Stream;

import br.com.arbo.steamside.steam.client.types.AppId;

public interface Data_appinfo_vdf
{

	Optional<AppInfo> get(AppId appid);

	Stream<AppId> everyAppId();
}
