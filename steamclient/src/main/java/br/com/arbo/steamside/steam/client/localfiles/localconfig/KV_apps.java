package br.com.arbo.steamside.steam.client.localfiles.localconfig;

import java.util.function.Consumer;
import java.util.stream.Stream;

import org.eclipse.jdt.annotation.Nullable;

import br.com.arbo.steamside.steam.client.types.AppId;

public interface KV_apps {

	Stream< ? extends KV_app> all();

	void forEach(Consumer<KV_app> each);

	@Nullable
	KV_app get(AppId appid);

}
