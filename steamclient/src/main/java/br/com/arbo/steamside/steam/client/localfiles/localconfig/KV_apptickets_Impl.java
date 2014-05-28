package br.com.arbo.steamside.steam.client.localfiles.localconfig;

import java.util.HashMap;
import java.util.function.Consumer;
import java.util.stream.Stream;

import br.com.arbo.steamside.steam.client.types.AppId;

public class KV_apptickets_Impl implements KV_apptickets {

	public void add(KV_appticket_Impl app)
	{
		this.put(app.appid, app);
	}

	@Override
	public Stream< ? extends KV_appticket> all()
	{
		@SuppressWarnings("unchecked")
		final Stream< ? extends KV_appticket> all = map.values()
				.stream();
		return all;
	}

	@Override
	public void forEach(Consumer<KV_appticket> visitor)
	{
		map.values().forEach(visitor);
	}

	private void put(AppId appid, KV_appticket_Impl app)
	{
		this.map.put(appid.appid, app);
	}

	private final HashMap<String, KV_appticket_Impl> map = new HashMap<>();

}
