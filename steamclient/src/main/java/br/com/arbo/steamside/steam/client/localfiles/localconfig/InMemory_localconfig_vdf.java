package br.com.arbo.steamside.steam.client.localfiles.localconfig;

public class InMemory_localconfig_vdf
implements Data_localconfig_vdf {

	@Override
	public KV_apps_Impl apps()
	{
		return kv_apps;
	}

	@Override
	public KV_apptickets_Impl apptickets()
	{
		return kv_apptickets;
	}

	private final KV_apps_Impl kv_apps = new KV_apps_Impl();

	private final KV_apptickets_Impl kv_apptickets = new KV_apptickets_Impl();

}
