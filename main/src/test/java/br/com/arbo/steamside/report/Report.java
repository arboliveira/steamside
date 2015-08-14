package br.com.arbo.steamside.report;

import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Stream;

public class Report
{

	private static void banner(String banner)
	{
		System.out.println("==============================");
		System.out.println(banner);
		System.out.println("==============================");
	}

	public Report(String group, Stream<String> lines)
	{
		this.group = group;
		this.lines = lines;
	}

	public void body()
	{
		lines.forEach(this::item);
	}

	public void print()
	{
		head();
		body();
		feet();
	}

	public void feet()
	{
		banner(i + " " + group);
	}

	public void head()
	{
		banner(group);
	}

	private void item(String s)
	{
		System.out.println(s);
		i.incrementAndGet();
	}

	private final Stream<String> lines;
	private final String group;
	private final AtomicInteger i = new AtomicInteger();

}
