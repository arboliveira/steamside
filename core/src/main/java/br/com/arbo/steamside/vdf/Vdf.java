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

		@Override
		public void replaceTokenBefore(
				final String previous, final String newvalue, final int position) {
			Vdf.this.replaceTokenBefore(previous, newvalue, position);
		}

	}

	void replaceTokenBefore(final String previous, final String newvalue,
			final int position) {
		final String part1 = content.substring(0, position);
		final String part2 = content.substring(position);
		final int i = part1.lastIndexOf(previous);
		final String part1a = part1.substring(0, i);
		final String part1b = part1.substring(i + previous.length());
		content = part1a + newvalue + part1b + part2;
	}

	PositionalStringReader readerPositionedInside() {
		return new PositionalStringReader(content);
	}

}
