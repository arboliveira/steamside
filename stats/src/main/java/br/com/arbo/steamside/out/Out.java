package br.com.arbo.steamside.out;

import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.Consumer;
import java.util.stream.Stream;

public class Out
{

	public void out()
	{
		head();
		body();
		feet();
	}

	private void banner(String s)
	{
		print.accept("==============================");
		print.accept(s);
		print.accept("==============================");
	}

	private void body()
	{
		lines.forEach(this::line);
	}

	private void feet()
	{
		banner(i + " " + name);
	}

	private void head()
	{
		banner(name);
	}

	private void line(String s)
	{
		print.accept(s);
		i.incrementAndGet();
	}

	public Out(String name, Stream<String> lines, Consumer<String> print)
	{
		this.name = name;
		this.lines = lines;
		this.print = print != null ? print : s -> {
		};
	}

	private final AtomicInteger i = new AtomicInteger();
	private final Stream<String> lines;
	private final String name;
	private final Consumer<String> print;
}
