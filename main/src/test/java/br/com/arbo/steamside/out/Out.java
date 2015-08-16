package br.com.arbo.steamside.out;

import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.Consumer;
import java.util.stream.Stream;

public class Out
{

	public Out(String name, Stream<String> lines)
	{
		this.name = name;
		this.lines = lines;
		this.print = System.out::println;
	}

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

	private final AtomicInteger i = new AtomicInteger();
	private final String name;
	private final Consumer<String> print;
	private final Stream<String> lines;
}
