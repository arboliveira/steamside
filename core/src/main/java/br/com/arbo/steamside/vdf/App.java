package br.com.arbo.steamside.vdf;

import br.com.arbo.java.io.PositionalStringReader;

public class App {

	private final Region content;

	public App(final Region content) {
		this.content = content;
	}

	public void category(final String newvalue) {
		final Region tags = content.region("tags");
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
			public void onSubRegion(final String k, final Region r) {
				// do nothing
			}

		}
		final CategoryChange categoryChange = new CategoryChange();
		tags.accept(categoryChange, reader);
		content.replaceTokenBefore(
				categoryChange.previous, newvalue, reader.position());
	}

	public boolean isFavorite() {
		final Region tags = content.region("tags");
		final PositionalStringReader reader = tags.reader();
		class FavoriteFinder implements KeyValueVisitor {

			boolean favorite;

			@Override
			public void onKeyValue(final String k, final String v)
					throws Finished {
				if ("favorite".equals(v)) {
					favorite = true;
					throw new Finished();
				}
			}

			@Override
			public void onSubRegion(final String k, final Region r)
					throws Finished {
				// do nothing
			}

		}
		final FavoriteFinder visitor = new FavoriteFinder();
		tags.accept(visitor, reader);
		return visitor.favorite;
	}

}
