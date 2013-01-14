package br.com.arbo.steamside.vdf;

import br.com.arbo.java.io.PositionalStringReader;

public interface ReaderFactory {

	PositionalStringReader readerPositionedInside();

	void replaceTokenBefore(String previous, String newvalue, int position);

}
