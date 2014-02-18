package br.com.arbo.steamside.steam.client.localfiles.localconfig;

import org.eclipse.jdt.annotation.NonNull;
import org.eclipse.jdt.annotation.Nullable;

import br.com.arbo.steamside.types.AppId;
import br.com.arbo.steamside.types.LastPlayed;

public interface KV_app {

	@NonNull
	AppId appid();

	@Nullable
	LastPlayed lastPlayed();

	public interface Visitor {

		void each(KV_app each);
	}
}
