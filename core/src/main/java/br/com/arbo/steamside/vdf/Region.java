package br.com.arbo.steamside.vdf;

import java.io.IOException;
import java.io.StreamTokenizer;

import br.com.arbo.java.io.PositionalStringReader;
import br.com.arbo.steamside.vdf.KeyValueVisitor.Finished;

public class Region {

	private final ReaderFactory parent;

	Region(final ReaderFactory rf) {
		this.parent = rf;
	}

	Region region(final String name) throws NotFound {
		class Find implements KeyValueVisitor {

			Region found;

			@Override
			public void onKeyValue(final String k, final String v)
					throws Finished {
				// Do nothing
			}

			@Override
			public void onSubRegion(final String k, final Region r)
					throws Finished {
				if (k.equalsIgnoreCase(name)) {
					found = r;
					throw new Finished();
				}
			}

		}

		final Find find = new Find();
		accept(find);
		if (find.found != null) return find.found;
		throw NotFound.name(name);
	}

	public void accept(final KeyValueVisitor visitor) {
		accept(visitor, reader());
	}

	PositionalStringReader reader() {
		return parent.readerPositionedInside();
	}

	void accept(final KeyValueVisitor visitor,
			final PositionalStringReader reader) {
		final StreamTokenizer tokenizer = StreamTokenizerBuilder.build(reader);
		accept(visitor, tokenizer);
	}

	private void accept(final KeyValueVisitor visitor,
			final StreamTokenizer tokenizer) {
		try {
			acceptX(visitor, tokenizer);
		} catch (final IOException e) {
			throw new RuntimeException(e);
		}
	}

	private void acceptX(final KeyValueVisitor visitor,
			final StreamTokenizer tokenizer)
			throws IOException {
		try {
			sweep(visitor, tokenizer);
		} catch (final Finished ok) {
			//
		}
	}

	private void sweep(final KeyValueVisitor visitor,
			final StreamTokenizer tokenizer)
			throws IOException, Finished {
		while (true)
			advance(visitor, tokenizer);
	}

	private void advance(final KeyValueVisitor visitor,
			final StreamTokenizer tokenizer)
			throws IOException, Finished {
		tokenizer.nextToken();
		final String key = tokenizer.sval;

		if (key == null) {
			final int ttype = tokenizer.ttype;
			if (ttype == '}' || ttype == StreamTokenizer.TT_EOF)
				throw new Finished();
			throw new RuntimeException();
		}

		tokenizer.nextToken();
		final String value = tokenizer.sval;

		if (value != null) {
			visitor.onKeyValue(key, value);
			return;
		}

		if (tokenizer.ttype == '{') {
			final Region sub = new Region(new RegionReaderFactory(key));
			visitor.onSubRegion(key, sub);
			skipPastEndOfRegion(tokenizer);
			return;
		}

		throw new RuntimeException();
	}

	private void skipPastEndOfRegion(final StreamTokenizer parser)
			throws IOException {
		class DoNothing implements KeyValueVisitor {

			@Override
			public void onKeyValue(final String k, final String v)
					throws Finished {
				// 
			}

			@Override
			public void onSubRegion(final String k, final Region r)
					throws Finished {
				// 
			}

		}
		acceptX(new DoNothing(), parser);
	}

	class RegionReaderFactory implements ReaderFactory {

		final String name;

		public RegionReaderFactory(final String name) {
			this.name = name;
		}

		@Override
		public PositionalStringReader readerPositionedInside() {
			class SkipToName implements KeyValueVisitor {

				@Override
				public void onKeyValue(final String k, final String v)
						throws Finished {
					// do nothing
				}

				@Override
				public void onSubRegion(final String k, final Region r)
						throws Finished {
					if (k.equals(name)) throw new Finished();
				}

			}
			final PositionalStringReader reader = reader();
			accept(new SkipToName(), reader);
			return reader;
		}

		@Override
		public void replaceTokenBefore(
				final String previous, final String newvalue, final int position) {
			Region.this.replaceTokenBefore(previous, newvalue, position);
		}

	}

	void replaceTokenBefore(final String previous, final String newvalue,
			final int position) {
		parent.replaceTokenBefore(previous, newvalue, position);
	}
}
