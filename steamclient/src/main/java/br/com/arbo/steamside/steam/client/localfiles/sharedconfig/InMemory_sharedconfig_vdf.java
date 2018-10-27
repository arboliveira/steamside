package br.com.arbo.steamside.steam.client.localfiles.sharedconfig;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Stream;

import br.com.arbo.steamside.steam.client.types.AppId;

public class InMemory_sharedconfig_vdf implements Data_sharedconfig_vdf
{

	public void add(Entry_app app)
	{
		this.map.put(app.id, app);
	}

	@Override
	public Stream<AppId> everyAppId()
	{
		return map.keySet().stream().map(AppId::new);
	}

	@Override
	public Optional<Entry_app> get(AppId appid)
	{
		return Optional.ofNullable(map.get(appid.appid));
	}

	private final Map<String, Entry_app> map = new HashMap<>();
}
