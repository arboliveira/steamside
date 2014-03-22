package br.com.arbo.steamside.vdf;

import java.io.Reader;
import java.io.StringReader;

class RootReaderFactory implements ReaderFactory {

	public RootReaderFactory(String content) {
		this.rootReader = new StringReader(content);
	}

	@Override
	public Reader newReaderPositionedInside()
	{
		return rootReader;
	}

	private final StringReader rootReader;
}