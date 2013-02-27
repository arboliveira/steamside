package br.com.arbo.steamside.vdf;

import br.com.arbo.java.io.PositionalStringReader;
import br.com.arbo.steamside.types.Category;

public class AppImpl implements App {

	private final RegionImpl content;

	public AppImpl(final RegionImpl content) {
		this.content = content;
	}

	public boolean isFavorite() {
		return isInCategory(new Category("favorite"));
	}

	@Override
	public boolean isInCategory(final Category name) {
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
