package br.com.arbo.steamside.out;

import java.io.PrintWriter;
import java.util.function.Consumer;

import org.apache.commons.io.output.StringBuilderWriter;

public class Dump
{
	public static String dumpToString(Consumer<Consumer<String>> data) {
		StringBuilderWriter dump = new StringBuilderWriter();
		try (PrintWriter p = new PrintWriter(dump))
		{
			data.accept(p::println);
		}
		return dump.getBuilder().toString();
	}
}
