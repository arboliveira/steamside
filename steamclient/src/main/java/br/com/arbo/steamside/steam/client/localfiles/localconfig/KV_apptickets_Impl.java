package br.com.arbo.steamside.steam.client.localfiles.localconfig;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Consumer;
import java.util.stream.Stream;

import br.com.arbo.steamside.steam.client.types.AppId;

public class KV_apptickets_Impl implements KV_apptickets
{

	public void add(KV_appticket app)
	{
		this.put(app.appid(), app);
	}

	@Override
	public Stream<KV_appticket> all()
	{
		return map.values().stream();
	}

	@Override
	public void forEach(Consumer<KV_appticket> visitor)
	{
		map.values().forEach(visitor);
	}

	@Override
	public Stream<AppId> streamAppId()
	{
		return map.keySet().stream().map(AppId::new);
	}

	private void put(AppId appid, KV_appticket app)
	{
		this.map.put(appid.appid, app);
	}

	private final Map<String, KV_appticket> map = new HashMap<>();

}
