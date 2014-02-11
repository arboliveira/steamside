package br.com.arbo.steamside.vdf;

import br.com.arbo.steamside.indent.Indent;

public class DumpVdfStructure implements
		KeyValueVisitor {

	Indent indent = new Indent();

	@Override
	public void onSubRegion(final String k, final Region r) {
		System.out.println(indent.on("[" + k + "]"));
		indent.increase();
		r.accept(this);
		indent.decrease();
	}

	@Override
	public void onKeyValue(final String k, final String v) throws Finished {
		System.out.println(indent.on("[" + k + "]'" + v + "'"));
	}

}
