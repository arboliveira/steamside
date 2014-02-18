package br.com.arbo.steamside.steam.client.localfiles.localconfig;

import java.util.HashMap;
import java.util.Map;

import br.com.arbo.steamside.steam.client.localfiles.localconfig.KV_app.Visitor;
import br.com.arbo.steamside.types.AppId;

public class Data_localconfig_vdf implements KV_apps {

	private final Map<String, KV_app_Impl> map =
			new HashMap<String, KV_app_Impl>();

	@Override
	public void accept(Visitor visitor) {
		for (KV_app_Impl app : map.values())
			visitor.each(app);
	}

	public void add(KV_app_Impl app) {
		this.put(app.appid, app);
	}

	private void put(AppId appid, KV_app_Impl app) {
		this.map.put(appid.appid, app);
	}

}
