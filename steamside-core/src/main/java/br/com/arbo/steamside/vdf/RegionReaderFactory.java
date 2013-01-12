package br.com.arbo.steamside.vdf;

import java.io.IOException;
import java.io.StreamTokenizer;

import br.com.arbo.steamside.java.io.PositionalStringReader;

public class RegionReaderFactory implements ReaderFactory {

	private final ReaderFactory parent;
	private final String name;

	public RegionReaderFactory(ReaderFactory parent, String name) {
		this.parent = parent;
		this.name = name;
	}

	@Override
	public PositionalStringReader readerPositionedInside() {
		PositionalStringReader reader = parent.readerPositionedInside();
		StreamTokenizer t = StreamTokenizerBuilder.build(reader);
		try {
			while (true) {
				// Key name
				t.nextToken();
				String key = t.sval;
				// Value or opening brace
				t.nextToken();
				if (key.equals(name)) break;
				String value = t.sval;
				if (value == null) {
					if (t.ttype == '{') {
						while (t.nextToken() != '}')
						{
							// keep trying
						}
					}
				}
			}
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
		return reader;
	}

}
