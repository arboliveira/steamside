package br.com.arbo.steamside.steam.client.localfiles.localconfig;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Consumer;

import br.com.arbo.steamside.types.AppId;

public class InMemory_localconfig_vdf
		implements Data_localconfig_vdf, KV_apps {

	@Override
	public KV_apps apps() {
		return this;
	}

	@Override
	public void forEach(Consumer<KV_app> visitor) {
		map.values().forEach(visitor);
	}

	public void add(KV_app_Impl app) {
		this.put(app.appid, app);
	}

	private void put(AppId appid, KV_app_Impl app) {
		this.map.put(appid.appid, app);
	}

	private final Map<String, KV_app_Impl> map =
			new HashMap<String, KV_app_Impl>();

	@Override
	public KV_app get(AppId appid) {
		return map.get(appid.appid);
	}

}
