package br.com.arbo.steamside.steam.client.localfiles.sharedconfig;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import br.com.arbo.steamside.types.AppId;

public class InMemory_sharedconfig_vdf
		implements Data_sharedconfig_vdf, R_apps {

	private final Map<String, Entry_app> map = new HashMap<String, Entry_app>();

	@Override
	public R_apps apps() {
		return this;
	}

	@SuppressWarnings("null")
	@Override
	public void accept(AppId.Visitor visitor) {
		Set<String> keys = map.keySet();
		for (String key : keys)
			visitor.each(new AppId(key));
	}

	@Override
	public void accept(Entry_app.Visitor visitor) {
		Collection<Entry_app> values = map.values();
		for (Entry_app app : values)
			visitor.each(app);
	}

	public void add(Entry_app app) {
		this.map.put(app.id, app);
	}

	@Override
	public Entry_app get(AppId appid) {
		return map.get(appid.appid);
	}
}
