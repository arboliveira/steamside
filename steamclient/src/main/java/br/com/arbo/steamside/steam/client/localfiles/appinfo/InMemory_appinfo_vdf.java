package br.com.arbo.steamside.steam.client.localfiles.appinfo;

import java.util.HashMap;
import java.util.Optional;
import java.util.stream.Stream;

import br.com.arbo.steamside.steam.client.apps.MissingFrom_appinfo_vdf;
import br.com.arbo.steamside.steam.client.types.AppId;

public class InMemory_appinfo_vdf implements Data_appinfo_vdf
{

	@Override
	public AppInfo get(final AppId appid) throws MissingFrom_appinfo_vdf
	{
		return Optional
			.ofNullable(
				map.get(appid.appid))
			.orElseThrow(
				() -> MissingFrom_appinfo_vdf.appid(appid));
	}

	public void put(String appid, AppInfo appinfo)
	{
		map.put(appid, appinfo);
	}

	@Override
	public Stream<AppId> streamAppId()
	{
		return map.keySet().stream().map(AppId::new);
	}

	public InMemory_appinfo_vdf()
	{
		this.map = new HashMap<String, AppInfo>();
	}

	final HashMap<String, AppInfo> map;

}
