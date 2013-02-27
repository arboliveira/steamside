package br.com.arbo.steamside.vdf;

import br.com.arbo.java.io.PositionalStringReader;
import br.com.arbo.steamside.types.Category;

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
			if ("LastPlayed".equalsIgnoreCase(k))
				app.lastPlayed = v;
			else if ("CloudEnabled".equalsIgnoreCase(k))
				app.cloudEnabled = v;
		}

		@Override
		public void onSubRegion(final String k, final RegionImpl r)
				throws Finished {
			if ("tags".equalsIgnoreCase(k))
				app.categories = new TagsRegion(r).parse();
		}
	}

	private boolean isFavorite() {
		return isInCategory(new Category("favorite"));
	}

	private boolean isInCategory(final Category name) {
		final RegionImpl tags;
		try {
			tags = content.region("tags");
		} catch (final NotFound e) {
			return false;
		}
		final PositionalStringReader reader = tags.reader();
		class Finder implements KeyValueVisitor {

			boolean found;

			@Override
			public void onKeyValue(final String k, final String v)
					throws Finished {
				if (name.equals(v)) {
					found = true;
					throw new Finished();
				}
			}

			@Override
			public void onSubRegion(final String k, final RegionImpl r)
					throws Finished {
				// do nothing
			}

		}
		final Finder visitor = new Finder();
		tags.accept(visitor, reader);
		return visitor.found;
	}

}
