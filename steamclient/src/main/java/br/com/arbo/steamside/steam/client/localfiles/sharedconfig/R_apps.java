package br.com.arbo.steamside.steam.client.localfiles.sharedconfig;

import java.util.function.Consumer;
import java.util.stream.Stream;

import br.com.arbo.steamside.steam.client.types.AppId;

public interface R_apps {

	Stream<AppId> streamAppId();

	void forEachAppId(Consumer<AppId> visitor);

	void forEachEntry_app(Consumer<Entry_app> visitor);

}
