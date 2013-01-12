package br.com.arbo.steamside.vdf;

import java.io.IOException;
import java.io.StreamTokenizer;

import br.com.arbo.steamside.java.io.PositionalStringReader;

public class RootReaderFactory implements ReaderFactory {

	private final Vdf vdf;

	RootReaderFactory(Vdf vdf) {
		this.vdf = vdf;
	}

	@Override
	public PositionalStringReader readerPositionedInside() {
		PositionalStringReader reader = new PositionalStringReader(
				vdf.content());
		StreamTokenizer t = StreamTokenizerBuilder.build(reader);
		try {
			// Root name
			t.nextToken();
			// Opening brace
			t.nextToken();
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
		return reader;
	}

}
