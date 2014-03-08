package br.com.arbo.steamside.steam.client.localfiles.localconfig;

import java.util.function.Consumer;

public interface KV_apps {

	void forEach(Consumer<KV_app> each);
}
