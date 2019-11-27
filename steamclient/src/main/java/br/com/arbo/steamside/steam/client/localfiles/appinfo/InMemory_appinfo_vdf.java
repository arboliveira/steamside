package br.com.arbo.steamside.steam.client.localfiles.appinfo;

import java.util.HashMap;
import java.util.Optional;
import java.util.stream.Stream;

import br.com.arbo.steamside.steam.client.types.AppId;

public class InMemory_appinfo_vdf implements Data_appinfo_vdf
{

	@Override
	public Stream<AppId> everyAppId()
	{
		return map.keySet().stream().map(AppId::new);
	}

	@Override
	public Optional<AppInfo> get(final AppId appid)
	{
		return Optional.ofNullable(map.get(appid.appid));
	}

	public void put(String appid, AppInfo appinfo)
	{
		map.put(appid, appinfo);
	}

	public InMemory_appinfo_vdf()
	{
		this.map = new HashMap<String, AppInfo>();
	}

	final HashMap<String, AppInfo> map;

}
