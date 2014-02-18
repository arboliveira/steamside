package br.com.arbo.steamside.steam.client.localfiles.localconfig;

import br.com.arbo.steamside.types.LastPlayed;
import br.com.arbo.steamside.vdf.KeyValueVisitor;
import br.com.arbo.steamside.vdf.Region;

class AppRegion {

	private final Region content;

	AppRegion(final Region content) {
		this.content = content;
	}

	KV_app_Impl parse() {
		final Hydrate hydrate = new Hydrate();
		content.accept(hydrate);
		return hydrate.app;
	}

	static final class Hydrate implements KeyValueVisitor {

		final KV_app_Impl app = new KV_app_Impl();

		@Override
		public void onKeyValue(final String k, final String v)
				throws Finished {
			if ("LastPlayed".equalsIgnoreCase(k))
				app.lastPlayed = new LastPlayed(v);
		}

		@Override
		public void onSubRegion(final String k, final Region r)
				throws Finished {
			// The app section doesn't have sub-regions
		}
	}

}