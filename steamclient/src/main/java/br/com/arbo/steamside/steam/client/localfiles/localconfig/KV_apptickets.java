package br.com.arbo.steamside.steam.client.localfiles.localconfig;

import java.util.function.Consumer;
import java.util.stream.Stream;

public interface KV_apptickets {

	Stream< ? extends KV_appticket> all();

	void forEach(Consumer<KV_appticket> visitor);

}
