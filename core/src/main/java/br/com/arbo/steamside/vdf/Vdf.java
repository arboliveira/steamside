package br.com.arbo.steamside.vdf;

import br.com.arbo.java.io.PositionalStringReader;

public class Vdf {

	String content;
	private final RegionImpl root;

	public Vdf(final String content) {
		this.content = content;
		this.root = new RegionImpl(new RootReaderFactory());
	}

	public String content() {
		return content;
	}

	public RegionImpl root() {
		return root;
	}

	class RootReaderFactory implements ReaderFactory {

		@Override
		public PositionalStringReader readerPositionedInside() {
			return Vdf.this.readerPositionedInside();
		}

	}

	PositionalStringReader readerPositionedInside() {
		return new PositionalStringReader(content);
	}

}
