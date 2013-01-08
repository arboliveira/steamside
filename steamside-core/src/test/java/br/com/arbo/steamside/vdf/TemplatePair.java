package br.com.arbo.steamside.vdf;

import java.io.IOException;

class TemplatePair {

	public final String before;
	public final String expected;

	TemplatePair(String prefix) throws IOException {
		this.before = read(prefix, "before");
		this.expected = read(prefix, "expected");
	}

	private static String read(String prefix, final String middle)
			throws IOException {
		final String name = prefix + "." + middle;
		return new Template(name).content;
	}
}