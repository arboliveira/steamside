package br.com.arbo.steamside.vdf.examples;

import java.io.File;
import java.io.IOException;

import org.apache.commons.io.FileUtils;

import br.com.arbo.steamside.steamclient.localfiles.steam.userdata.SharedconfigVdfFile;
import br.com.arbo.steamside.vdf.KeyValueVisitor;
import br.com.arbo.steamside.vdf.Region;
import br.com.arbo.steamside.vdf.Vdf;

final class DumpVdfStructure implements
		KeyValueVisitor {

	public static void main(final String[] args) throws IOException {
		final File from = SharedconfigVdfFile.sharedconfig_vdf();
		final String text = FileUtils.readFileToString(from);
		final Vdf vdf = new Vdf(text);
		vdf.root().accept(new DumpVdfStructure());
	}

	@Override
	public void onSubRegion(final String k, final Region r) {
		System.out.println(k + ": REGION");
		r.accept(this);
	}

	@Override
	public void onKeyValue(final String k, final String v) throws Finished {
		System.out.println(k + ": " + v);
	}
}