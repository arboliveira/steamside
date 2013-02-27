package br.com.arbo.steamside.vdf;

import java.util.Collection;
import java.util.HashSet;

class TagsRegion {

	private final RegionImpl content;

	TagsRegion(final RegionImpl content) {
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
		public void onSubRegion(final String k, final RegionImpl r)
				throws Finished {
			// The "tags" region has no sub-regions of itself.
		}

	}
}
