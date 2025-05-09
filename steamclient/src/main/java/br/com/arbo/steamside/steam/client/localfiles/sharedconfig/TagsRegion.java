package br.com.arbo.steamside.steam.client.localfiles.sharedconfig;

import java.util.Collection;
import java.util.HashSet;

import br.com.arbo.steamside.steam.client.localfiles.vdf.KeyValueVisitor;
import br.com.arbo.steamside.steam.client.localfiles.vdf.Region;

class TagsRegion {

	private final Region content;

	TagsRegion(final Region content) {
		this.content = content;
	}

	Collection<String> parse() {
		final Hydrate h = new Hydrate();
		content.accept(h);
		return h.tags;
	}

	static class Hydrate implements KeyValueVisitor {

		final Collection<String> tags = new HashSet<String>();

		@Override
		public void onKeyValue(final String k, final String v) throws Finished {
			tags.add(v);
		}

		@Override
		public void onSubRegion(final String k, final Region r)
				throws Finished {
			// The "tags" region has no sub-regions of itself.
		}

	}
}
