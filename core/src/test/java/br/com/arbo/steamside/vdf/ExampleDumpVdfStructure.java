package br.com.arbo.steamside.vdf;

import java.io.File;
import java.io.IOException;

import org.apache.commons.io.FileUtils;

import br.com.arbo.steamside.steam.client.localfiles.sharedconfig.File_sharedconfig_vdf;

public final class ExampleDumpVdfStructure implements
		KeyValueVisitor {

	public static void main(final String[] args) throws IOException {
		final File from = File_sharedconfig_vdf.sharedconfig_vdf();
		final String text = FileUtils.readFileToString(from);
		final Vdf vdfImpl = new Vdf(text);
		vdfImpl.root().accept(new ExampleDumpVdfStructure());
	}

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