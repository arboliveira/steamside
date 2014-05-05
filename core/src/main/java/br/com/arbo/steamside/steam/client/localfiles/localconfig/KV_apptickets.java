package br.com.arbo.steamside.steam.client.localfiles.localconfig;

import java.util.function.Consumer;

public interface KV_apptickets {

	void forEach(Consumer<KV_appticket> visitor);

}
