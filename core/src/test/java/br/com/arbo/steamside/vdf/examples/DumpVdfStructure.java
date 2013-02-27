package br.com.arbo.steamside.vdf.examples;

import java.io.File;
import java.io.IOException;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.StringUtils;

import br.com.arbo.steamside.steam.client.localfiles.sharedconfig.File_sharedconfig_vdf;
import br.com.arbo.steamside.vdf.KeyValueVisitor;
import br.com.arbo.steamside.vdf.RegionImpl;
import br.com.arbo.steamside.vdf.VdfImpl;

final class DumpVdfStructure implements
		KeyValueVisitor {

	public static void main(final String[] args) throws IOException {
		final File from = File_sharedconfig_vdf.sharedconfig_vdf();
		final String text = FileUtils.readFileToString(from);
		final VdfImpl vdfImpl = new VdfImpl(text);
		vdfImpl.root().accept(new DumpVdfStructure());
	}

	String indent = "";

	@Override
	public void onSubRegion(final String k, final RegionImpl r) {
		System.out.println(indent + "[" + k + "]");
		indent = indent(indent.length() + 1);
		r.accept(this);
		indent = indent(indent.length() - 1);
	}

	@Override
	public void onKeyValue(final String k, final String v) throws Finished {
		System.out.println(indent + k + ": " + v);
	}

	private static String indent(final int depth) {
		return StringUtils.repeat(' ', depth);
	}
}