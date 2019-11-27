package br.com.arbo.steamside.steam.client.vdf;

import java.io.InputStream;
import java.util.function.Consumer;

import br.com.arbo.steamside.indent.Indent;
import br.com.arbo.steamside.steam.client.localfiles.vdf.KeyValueVisitor;
import br.com.arbo.steamside.steam.client.localfiles.vdf.Region;
import br.com.arbo.steamside.steam.client.localfiles.vdf.VdfStructure;

public class DumpVdfStructure implements KeyValueVisitor
{

	public static void dump(InputStream in, Consumer<String> print)
	{
		new VdfStructure(in).root().accept(new DumpVdfStructure(print));
	}

	@Override
	public void onKeyValue(final String k, final String v) throws Finished
	{
		print(indent.on("[" + k + "]'" + v + "'"));
	}

	private void print(String s)
	{
		if (print != null)
			print.accept(s);
	}

	@Override
	public void onSubRegion(final String k, final Region r)
	{
		print(indent.on("[" + k + "]"));
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
