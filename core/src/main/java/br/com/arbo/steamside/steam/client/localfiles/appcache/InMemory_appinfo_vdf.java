package br.com.arbo.steamside.steam.client.localfiles.appcache;

import java.util.HashMap;

import br.com.arbo.steamside.steam.client.localfiles.appcache.Parse_appinfo_vdf.Visitor;

public class InMemory_appinfo_vdf {

	final HashMap<String, AppInfo> map;

	public InMemory_appinfo_vdf() {
		this.map = new HashMap<String, AppInfo>();
		new Parse_appinfo_vdf(Content_appinfo_vdf.content()).parse(
				new Visitor() {

					@Override
					public void each(final String appid, final AppInfo appinfo) {
						map.put(appid, appinfo);
					}
				});
	}

	AppInfo get(final String appid) {
		return map.get(appid);
	}
}
