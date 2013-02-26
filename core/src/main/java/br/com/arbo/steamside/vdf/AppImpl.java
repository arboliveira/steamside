package br.com.arbo.steamside.vdf;

import br.com.arbo.java.io.PositionalStringReader;

public class AppImpl implements App {

	private final RegionImpl content;

	public AppImpl(final RegionImpl content) {
		this.content = content;
	}

	public void category(final String newvalue) {
		final RegionImpl tags;
		try {
			tags = content.region("tags");
		} catch (final NotFound e) {
			// TODO Create tags region when it does not exist
			throw new RuntimeException(e);
		}
		final PositionalStringReader reader = tags.reader();
		class CategoryChange implements KeyValueVisitor {

			String previous;

			@Override
			public void onKeyValue(final String k, final String v)
					throws Finished {
				previous = v;
				throw new Finished();
			}

			@Override
			public void onSubRegion(final String k, final RegionImpl r) {
				// do nothing
			}

		}
		final CategoryChange categoryChange = new CategoryChange();
		tags.accept(categoryChange, reader);
		content.replaceTokenBefore(
				categoryChange.previous, newvalue, reader.position());
	}

	public boolean isFavorite() {
		return isInCategory("favorite");
	}

	@Override
	public boolean isInCategory(final String name) {
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
