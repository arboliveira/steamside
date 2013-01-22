package br.com.arbo.steamside.vdf;

import br.com.arbo.java.io.PositionalStringReader;

public class App {

	private final Region content;

	public App(Region content) {
		this.content = content;
	}

	public void category(String newvalue) {
		final Region tags = content.region("tags");
		final PositionalStringReader reader = tags.reader();
		class CategoryChange implements KeyValueVisitor {

			String previous;

			@Override
			public void onKeyValue(String k, String v) throws Finished {
				previous = v;
				throw new Finished();
			}

			@Override
			public void onSubRegion(String k, Region r) {
				// do nothing
			}

		}
		CategoryChange categoryChange = new CategoryChange();
		tags.accept(categoryChange, reader);
		content.replaceTokenBefore(
				categoryChange.previous, newvalue, reader.position());
	}

}
