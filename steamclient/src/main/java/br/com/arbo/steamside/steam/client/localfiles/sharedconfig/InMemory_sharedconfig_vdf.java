package br.com.arbo.steamside.steam.client.localfiles.sharedconfig;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.function.Consumer;
import java.util.stream.Stream;

import br.com.arbo.steamside.steam.client.types.AppId;

public class InMemory_sharedconfig_vdf
		implements Data_sharedconfig_vdf, R_apps {

	public void add(Entry_app app)
	{
		this.map.put(app.id, app);
	}

	@Override
	public R_apps apps()
	{
		return this;
	}

	@Override
	public void forEachAppId(Consumer<AppId> visitor)
	{
		map.keySet().stream().map(key -> new AppId(key)).forEach(visitor);
	}

	@Override
	public void forEachEntry_app(Consumer<Entry_app> visitor)
	{
		map.values().forEach(visitor);
	}

	@Override
	public Optional<Entry_app> get(AppId appid)
	{
		return Optional.ofNullable(map.get(appid.appid));
	}

	@Override
	public Stream<AppId> streamAppId()
	{
		return map.keySet().stream().map(key -> new AppId(key));
	}

	private final Map<String, Entry_app> map = new HashMap<>();
}
