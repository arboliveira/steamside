package br.com.arbo.steamside.steam.client.localfiles.localconfig;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Consumer;

import br.com.arbo.steamside.types.AppId;

class KV_apps_Impl implements KV_apps
{

	public void add(KV_app_Impl app)
	{
		this.put(app.appid, app);
	}

	@Override
	public void forEach(Consumer<KV_app> visitor)
	{
		map.values().forEach(visitor);
	}

	@Override
	public KV_app get(AppId appid)
	{
		return map.get(appid.appid);
	}

	private void put(AppId appid, KV_app_Impl app)
	{
		this.map.put(appid.appid, app);
	}

	private final Map<String, KV_app_Impl> map = new HashMap<>();

}