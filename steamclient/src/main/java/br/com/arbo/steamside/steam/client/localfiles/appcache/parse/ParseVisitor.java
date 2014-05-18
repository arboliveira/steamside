package br.com.arbo.steamside.steam.client.localfiles.appcache.parse;

import br.com.arbo.steamside.steam.client.localfiles.appcache.entry.AppInfo;

public interface ParseVisitor {

	void each(String appid, AppInfo appinfo);
}