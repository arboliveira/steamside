package br.com.arbo.steamside.steam.client.localfiles.localconfig;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.function.Consumer;
import java.util.stream.Stream;

import br.com.arbo.steamside.steam.client.types.AppId;

class KV_apps_Impl implements KV_apps
{

	public void add(KV_app_Impl app)
	{
		this.put(app.appid, app);
	}

	@Override
	public Stream< ? extends KV_app> all()
	{
		@SuppressWarnings("unchecked")
		final Stream<KV_app_Impl> stream = map.values().stream();
		final Stream< ? extends KV_app> all = stream;
		return all;
	}

	@Override
	public void forEach(Consumer<KV_app> visitor)
	{
		map.values().forEach(visitor);
	}

	@Override
	public Optional<KV_app> get(AppId appid)
	{
		return Optional.ofNullable(map.get(appid.appid));
	}

	@Override
	public Stream<AppId> streamAppId()
	{
		return map.keySet().stream().map(key -> new AppId(key));
	}

	private void put(AppId appid, KV_app_Impl app)
	{
		this.map.put(appid.appid, app);
	}

	private final Map<String, KV_app_Impl> map = new HashMap<>();

}