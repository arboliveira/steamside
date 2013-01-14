package br.com.arbo.steamside.vdf;

import java.io.IOException;
import java.io.StreamTokenizer;

import br.com.arbo.java.io.PositionalStringReader;
import br.com.arbo.steamside.vdf.KeyValueVisitor.Finished;

class Region {

	private final ReaderFactory parent;

	Region(ReaderFactory rf) {
		this.parent = rf;
	}

	Region region(final String name) {
		class Find implements KeyValueVisitor {

			Region found;

			@Override
			public void onKeyValue(String k, String v) throws Finished {
				// Do nothing
			}

			@Override
			public void onSubRegion(String k, Region r) throws Finished {
				if (k.equals(name)) {
					found = r;
					throw new Finished();
				}
			}

		}

		Find find = new Find();
		accept(find);
		if (find.found != null) return find.found;
		throw new RuntimeException("No sub-region with name: " + name);
	}

	void accept(KeyValueVisitor visitor) {
		accept(visitor, reader());
	}

	PositionalStringReader reader() {
		return parent.readerPositionedInside();
	}

	void accept(KeyValueVisitor visitor,
			final PositionalStringReader reader) {
		StreamTokenizer tokenizer = StreamTokenizerBuilder.build(reader);
		accept(visitor, tokenizer);
	}

	private void accept(KeyValueVisitor visitor, StreamTokenizer tokenizer) {
		try {
			acceptX(visitor, tokenizer);
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}

	private void acceptX(KeyValueVisitor visitor, StreamTokenizer tokenizer)
			throws IOException {
		try {
			sweep(visitor, tokenizer);
		} catch (Finished ok) {
			//
		}
	}

	private void sweep(KeyValueVisitor visitor, StreamTokenizer tokenizer)
			throws IOException, Finished {
		while (true)
			advance(visitor, tokenizer);
	}

	private void advance(KeyValueVisitor visitor, StreamTokenizer tokenizer)
			throws IOException, Finished {
		tokenizer.nextToken();
		String key = tokenizer.sval;

		if (key == null) {
			int ttype = tokenizer.ttype;
			if (ttype == '}' || ttype == StreamTokenizer.TT_EOF)
				throw new Finished();
			throw new RuntimeException();
		}

		tokenizer.nextToken();
		String value = tokenizer.sval;

		if (value != null) {
			visitor.onKeyValue(key, value);
			return;
		}

		if (tokenizer.ttype == '{') {
			Region sub = new Region(new RegionReaderFactory(key));
			visitor.onSubRegion(key, sub);
			skipPastEndOfRegion(tokenizer);
			return;
		}

		throw new RuntimeException();
	}

	private void skipPastEndOfRegion(StreamTokenizer parser)
			throws IOException {
		class DoNothing implements KeyValueVisitor {

			@Override
			public void onKeyValue(String k, String v) throws Finished {
				// 
			}

			@Override
			public void onSubRegion(String k, Region r) throws Finished {
				// 
			}

		}
		acceptX(new DoNothing(), parser);
	}

	class RegionReaderFactory implements ReaderFactory {

		final String name;

		public RegionReaderFactory(String name) {
			this.name = name;
		}

		@Override
		public PositionalStringReader readerPositionedInside() {
			class SkipToName implements KeyValueVisitor {

				@Override
				public void onKeyValue(String k, String v) throws Finished {
					// do nothing
				}

				@Override
				public void onSubRegion(String k, Region r) throws Finished {
					if (k.equals(name)) throw new Finished();
				}

			}
			PositionalStringReader reader = reader();
			accept(new SkipToName(), reader);
			return reader;
		}

		@Override
		public void replaceTokenBefore(
				String previous, String newvalue, int position) {
			Region.this.replaceTokenBefore(previous, newvalue, position);
		}

	}

	void replaceTokenBefore(String previous, String newvalue, int position) {
		parent.replaceTokenBefore(previous, newvalue, position);
	}
}
