package br.com.arbo.steamside.steam.client.localfiles.sharedconfig;

import br.com.arbo.steamside.steam.client.vdf.KeyValueVisitor;
import br.com.arbo.steamside.steam.client.vdf.Region;

class AppRegion {

	private final Region content;

	AppRegion(final Region content) {
		this.content = content;
	}

	Entry_app parse() {
		final Hydrate hydrate = new Hydrate();
		content.accept(hydrate);
		return hydrate.app;
	}

	static final class Hydrate implements KeyValueVisitor {

		final Entry_app app = new Entry_app();

		@Override
		public void onKeyValue(final String k, final String v)
				throws Finished {
			if ("LastPlayed".equalsIgnoreCase(k))
				app.sLastPlayed = v;
			else if ("CloudEnabled".equalsIgnoreCase(k))
				app.sCloudEnabled = v;
		}

		@Override
		public void onSubRegion(final String k, final Region r)
				throws Finished {
			if ("tags".equalsIgnoreCase(k))
				app.tags = new TagsRegion(r).parse();
		}
	}

}