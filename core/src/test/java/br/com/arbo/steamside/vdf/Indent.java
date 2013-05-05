package br.com.arbo.steamside.vdf;

import org.apache.commons.lang3.StringUtils;

class Indent {

	String indent = "";

	private static String indent(final int depth) {
		return StringUtils.repeat(' ', depth);
	}

	public void increase() {
		indent = indent(indent.length() + 1);
	}

	public void decrease() {
		indent = indent(indent.length() - 1);

	}

	public String on(final Object x) {
		return indent + x;
	}
}