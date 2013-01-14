package br.com.arbo.steamside.vdf;

import br.com.arbo.java.io.PositionalStringReader;

public class Vdf {

	String content;
	private final Region root;

	public Vdf(String content) {
		this.content = content;
		this.root = new Region(new RootReaderFactory());
	}

	public Region region(String name) {
		return root.region(name);
	}

	public String content() {
		return content;
	}

	Region root() {
		return root;
	}

	class RootReaderFactory implements ReaderFactory {

		@Override
		public PositionalStringReader readerPositionedInside() {
			return Vdf.this.readerPositionedInside();
		}

		@Override
		public void replaceTokenBefore(
				String previous, String newvalue, int position) {
			Vdf.this.replaceTokenBefore(previous, newvalue, position);
		}

	}

	void replaceTokenBefore(String previous, String newvalue, int position) {
		String part1 = content.substring(0, position);
		String part2 = content.substring(position);
		int i = part1.lastIndexOf(previous);
		String part1a = part1.substring(0, i);
		String part1b = part1.substring(i + previous.length());
		content = part1a + newvalue + part1b + part2;
	}

	PositionalStringReader readerPositionedInside() {
		return new PositionalStringReader(content);
	}

}
