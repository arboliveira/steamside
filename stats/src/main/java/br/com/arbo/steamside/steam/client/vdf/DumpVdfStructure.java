package br.com.arbo.steamside.steam.client.vdf;

import java.io.InputStream;
import java.util.function.Consumer;

import br.com.arbo.steamside.indent.Indent;

public class DumpVdfStructure implements KeyValueVisitor
{

	public static void dump(final InputStream in)
	{
		new Vdf(in).root().accept(new DumpVdfStructure(System.out::println));
	}

	@Override
	public void onKeyValue(final String k, final String v) throws Finished
	{
		print.accept(indent.on("[" + k + "]'" + v + "'"));
	}

	@Override
	public void onSubRegion(final String k, final Region r)
	{
		print.accept(indent.on("[" + k + "]"));
		indent.increase();
		r.accept(this);
		indent.decrease();
	}

	public DumpVdfStructure(Consumer<String> print)
	{
		this.print = print;
	}

	private final Indent indent = new Indent();
	private final Consumer<String> print;
}
