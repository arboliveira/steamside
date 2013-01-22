package br.com.arbo.steamside.vdf;

import java.io.Reader;
import java.io.StreamTokenizer;

class StreamTokenizerBuilder {

	static StreamTokenizer build(Reader reader) {
		StreamTokenizer parser = new StreamTokenizer(reader);
		parser.resetSyntax();
		parser.eolIsSignificant(false);
		parser.lowerCaseMode(false);
		parser.slashSlashComments(true);
		parser.slashStarComments(false);
		parser.commentChar('/');
		parser.quoteChar('"');
		parser.whitespaceChars('\u0000', '\u0020');
		parser.wordChars('A', 'Z');
		parser.wordChars('a', 'z');
		parser.wordChars('\u00A0', '\u00FF');
		return parser;
	}
}
