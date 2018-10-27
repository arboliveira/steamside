package br.com.arbo.steamside.steam.client.localfiles.appinfo;

public interface ParseVisitor {

	void each(String appid, AppInfo appinfo);
}