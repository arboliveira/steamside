package br.com.arbo.steamside.steam.client.localfiles.localconfig;

import java.util.function.Consumer;

import org.eclipse.jdt.annotation.Nullable;

import br.com.arbo.steamside.types.AppId;

public interface KV_apps {

	void forEach(Consumer<KV_app> each);

	@Nullable
	KV_app get(AppId appid);

}
