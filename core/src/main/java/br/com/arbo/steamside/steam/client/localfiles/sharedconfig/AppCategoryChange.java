package br.com.arbo.steamside.steam.client.localfiles.sharedconfig;

import br.com.arbo.java.io.PositionalStringReader;
import br.com.arbo.steamside.vdf.KeyValueVisitor;
import br.com.arbo.steamside.vdf.NotFound;
import br.com.arbo.steamside.vdf.Region;
import br.com.arbo.steamside.vdf.RegionImpl;

public class AppCategoryChange {

	private final RegionImpl content;

	public AppCategoryChange(final RegionImpl content) {
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
			public void onSubRegion(final String k, final Region r) {
				// do nothing
			}

		}
		final CategoryChange categoryChange = new CategoryChange();
		tags.accept(categoryChange, reader);
		content.replaceTokenBefore(
				categoryChange.previous, newvalue, reader.position());
	}

}