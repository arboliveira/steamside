package br.com.arbo.steamside.steam.client.localfiles.appinfo;

import java.util.stream.Stream;

import br.com.arbo.steamside.steam.client.apps.MissingFrom_appinfo_vdf;
import br.com.arbo.steamside.steam.client.types.AppId;

public interface Data_appinfo_vdf
{

	AppInfo get(final AppId appid) throws MissingFrom_appinfo_vdf;

	Stream<AppId> streamAppId();
}
