package br.com.arbo.steamside.steam.client.localfiles.sharedconfig;

import br.com.arbo.steamside.vdf.KeyValueVisitor;
import br.com.arbo.steamside.vdf.Region;

class AppRegion {

	private final Region content;

	AppRegion(final Region content) {
		this.content = content;
	}

	public App.Builder parse() {
		final Hydrate hydrate = new Hydrate();
		content.accept(hydrate);
		return hydrate.app;
	}

	static final class Hydrate implements KeyValueVisitor {

		final App.Builder app = new App.Builder();

		@Override
		public void onKeyValue(final String k, final String v)
				throws Finished {
			if ("LastPlayed".equalsIgnoreCase(k))
				app.lastPlayed(v);
			else if ("CloudEnabled".equalsIgnoreCase(k))
				app.cloudEnabled(v);
		}

		@Override
		public void onSubRegion(final String k, final Region r)
				throws Finished {
			if ("tags".equalsIgnoreCase(k))
				app.categories(new TagsRegion(r).parse());
		}
	}

}