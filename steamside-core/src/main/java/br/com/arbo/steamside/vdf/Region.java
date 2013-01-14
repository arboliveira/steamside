package br.com.arbo.steamside.vdf;

import java.io.IOException;
import java.io.StreamTokenizer;

import br.com.arbo.java.io.PositionalStringReader;

class Region {

	private final Vdf vdf;
	private final Token head;
	private final int from;
	private final String name;
	final ReaderFactory parent;

	Region(ReaderFactory rf) {
		this.parent = rf;
		this.from = -1;
		this.vdf = null;
		this.head = null;
		this.name = null;
	}

	/*
	Region(Vdf vdf, int from) {
		this.vdf = vdf;
		this.from = from;
		this.head = vdf.seek(from);
		this.name = null;
	}

	Region(Region parent, String name) {
		this.vdf = parent.vdf;
		this.name = name;
		this.from = -1;
		this.head = null;
	}
	*/

	Region region(final String name) {
		class Find implements KeyValueVisitor {

			Region found;

			@Override
			public void onKeyValue(String k, String v) {
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

		/*
		final int body = head.to + 1;
		int pos = body;
		while (true) {
			Token next = vdf.seek(pos);
			if (next.text().equals(name))
				return new Region(vdf, next.from);
			pos = next.to + 1;
		}
		*/
	}

	interface KeyValueVisitor {

		void onKeyValue(String k, String v);

		void onSubRegion(String k, Region r) throws Finished;
	}

	/*
	void accept(KeyValueVisitor visitor) {
		final int body = head.to + 1;
		int pos = body;
		while (true) {
			Token key = vdf.seek(pos);
			Token value = vdf.seek(key.to + 1);
			visitor.visit(new KeyValue(key, value));
		}
	}
	*/

	void accept(KeyValueVisitor visitor) {
		final PositionalStringReader reader = parent.readerPositionedInside();
		StreamTokenizer tokenizer = StreamTokenizerBuilder.build(reader);
		accept(visitor, tokenizer);
	}

	void accept(KeyValueVisitor visitor, StreamTokenizer tokenizer) {
		try {
			acceptX(visitor, tokenizer);
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}

	private void acceptX(KeyValueVisitor visitor, StreamTokenizer tokenizer)
			throws IOException {
		try {
			while (true)
				advance(visitor, tokenizer);
		} catch (Finished ok) {
			//
		}
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

	private void skipPastEndOfRegion(StreamTokenizer parser) throws IOException {
		acceptX(new DoNothing(), parser);
	}

	static class DoNothing implements KeyValueVisitor {

		@Override
		public void onKeyValue(String k, String v) {
			// 
		}

		@Override
		public void onSubRegion(String k, Region r) throws Finished {
			// 
		}

	}

	static class Finished extends Throwable {
		//
	}

	class RegionReaderFactory implements ReaderFactory {

		final String name;

		public RegionReaderFactory(String name) {
			this.name = name;
		}

		@Override
		public PositionalStringReader readerPositionedInside() {
			PositionalStringReader reader = parent.readerPositionedInside();
			StreamTokenizer t = StreamTokenizerBuilder.build(reader);

			class SkipToName implements KeyValueVisitor {

				@Override
				public void onKeyValue(String k, String v) {
					// do nothing
				}

				@Override
				public void onSubRegion(String k, Region r) throws Finished {
					if (k.equals(name)) throw new Finished();
				}

			}

			accept(new SkipToName(), t);
			return reader;
		}

	}
}
