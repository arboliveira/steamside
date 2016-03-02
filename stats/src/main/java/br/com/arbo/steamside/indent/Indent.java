package br.com.arbo.steamside.indent;

import org.apache.commons.lang3.StringUtils;

public class Indent
{

	private static String indent(final int depth)
	{
		return StringUtils.repeat(' ', depth);
	}

	public void decrease()
	{
		indent = indent(indent.length() - 1);
	}

	public void increase()
	{
		indent = indent(indent.length() + 1);
	}

	public String on(final Object x)
	{
		return indent + x;
	}

	private String indent = "";

}