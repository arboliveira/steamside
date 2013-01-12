package br.com.arbo.steamside.vdf;

import java.io.IOException;
import java.io.StreamTokenizer;

import br.com.arbo.steamside.java.io.PositionalStringReader;

class Region {

	private final Vdf vdf;
	private final Token head;
	private final int from;
	private final Region parent;
	private final String name;
	private ReaderFactory rf;

	Region(ReaderFactory rf) {
		this.rf = rf;
		this.from = -1;
		this.vdf = null;
		this.head = null;
		this.parent = null;
		this.name = null;
	}

	Region(Vdf vdf, int from) {
		this.vdf = vdf;
		this.from = from;
		this.head = vdf.seek(from);
		this.parent = null;
		this.name = null;
	}

	Region(Region parent, String name) {
		this.vdf = parent.vdf;
		this.parent = parent;
		this.name = name;
		this.from = -1;
		this.head = null;
	}

	Region region(String name) {
		final int body = head.to + 1;
		int pos = body;
		while (true) {
			Token next = vdf.seek(pos);
			if (next.text().equals(name))
				return new Region(vdf, next.from);
			pos = next.to + 1;
		}
	}

	interface KeyValueVisitor {

		void onKeyValue(String k, String v);

		void onSubRegion(String k, Region r);
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
		try {
			final PositionalStringReader reader =
					rf.readerPositionedInside();
			if (parent != null) {
				//parent.skipIntoRegion(reader, this.name);
				//int begin = 666; //parent.positionOfRegion(this.name);
				//reader.skip(begin);
			}
			StreamTokenizer parser = StreamTokenizerBuilder.build(reader);

			// Read node name
			parser.nextToken();
			String name = parser.sval;

			parser.nextToken();
			String value = parser.sval;

			// Check if next token is value or open of branch
			if (value != null)
			{
				// If token is value, read value
				visitor.onKeyValue(name, value);

			} else if (parser.ttype == '{')
			{
				// If token is open branch, read child nodes

				Region sub = new Region(new RegionReaderFactory(rf, name));
				visitor.onSubRegion(name, sub);

				while (parser.nextToken() != '}')
				{
					// keep trying
				}
			}
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}
}
