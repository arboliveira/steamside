package br.com.arbo.steamside.indent;

import org.apache.commons.lang3.StringUtils;

public class Indent {

	public void increase() {
		indent = indent(indent.length() + 1);
	}

	public void decrease() {
		indent = indent(indent.length() - 1);
	}

	public String on(final Object x) {
		return indent + x;
	}

	private static String indent(final int depth) {
		return StringUtils.repeat(' ', depth);
	}

	private String indent = "";

}