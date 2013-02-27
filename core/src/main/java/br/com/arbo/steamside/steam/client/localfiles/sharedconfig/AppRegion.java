package br.com.arbo.steamside.steam.client.localfiles.sharedconfig;

import br.com.arbo.steamside.vdf.KeyValueVisitor;
import br.com.arbo.steamside.vdf.RegionImpl;

class AppRegion {

	private final RegionImpl content;

	AppRegion(final RegionImpl content) {
		this.content = content;
	}

	public App parse() {
		final Hydrate hydrate = new Hydrate();
		content.accept(hydrate);
		return hydrate.app;
	}

	static final class Hydrate implements KeyValueVisitor {

		final App app = new App();

		@Override
		public void onKeyValue(final String k, final String v)
				throws Finished {
			app.onKeyValue(k, v);
		}

		@Override
		public void onSubRegion(final String k, final RegionImpl r)
				throws Finished {
			if ("tags".equalsIgnoreCase(k))
				app.categories = new TagsRegion(r).parse();
		}
	}

}