package br.com.arbo.steamside.steam.client.types;

import br.com.arbo.steamside.steam.client.apps.MissingFrom_appinfo_vdf;

public interface AppNameType {

	AppId appid();

	String appnametype() throws MissingFrom_appinfo_vdf;
}
