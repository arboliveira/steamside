package br.com.arbo.java.io;

import java.io.IOException;
import java.io.StringReader;

public class PositionalStringReader extends StringReader {

	public PositionalStringReader(String s) {
		super(s);
	}

	@Override
	public int read() throws IOException {
		int c = super.read();
		if (c != -1) next++;
		return c;
	}

	@Override
	public int read(char[] cbuf, int off, int len) throws IOException {
		int n = super.read(cbuf, off, len);
		if (n != -1) next += n;
		return n;
	}

	@Override
	public long skip(long ns) throws IOException {
		final long n = super.skip(ns);
		next += n;
		return n;
	}

	public int position() {
		return next;
	}

	private int next;
}
