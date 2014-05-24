package br.com.arbo.steamside.vdf;

import java.io.InputStream;

import br.com.arbo.steamside.indent.Indent;

public class DumpVdfStructure implements KeyValueVisitor {

	public static void dump(final InputStream in)
	{
		new Vdf(in).root().accept(new DumpVdfStructure());
	}

	@Override
	public void onKeyValue(final String k, final String v) throws Finished
	{
		System.out.println(indent.on("[" + k + "]'" + v + "'"));
	}

	@Override
	public void onSubRegion(final String k, final Region r)
	{
		System.out.println(indent.on("[" + k + "]"));
		indent.increase();
		r.accept(this);
		indent.decrease();
	}

	Indent indent = new Indent();

}
