package br.com.arbo.steamside.vdf;

import java.io.File;
import java.io.IOException;

import org.apache.commons.io.FileUtils;

import br.com.arbo.steamside.steam.client.localfiles.localconfig.File_localconfig_vdf;

public final class ExampleDumpVdfStructure {

	private final File from;

	public ExampleDumpVdfStructure(final File from) {
		this.from = from;
	}

	public static void main(final String[] args) throws IOException {
		final File from =
				File_localconfig_vdf.localconfig_vdf();
		//File_sharedconfig_vdf.sharedconfig_vdf();
		new ExampleDumpVdfStructure(from).dump();
	}

	private void dump() throws IOException {
		final String text = FileUtils.readFileToString(from);
		final Vdf vdfImpl = new Vdf(text);
		vdfImpl.root().accept(new DumpVdfStructure());
	}

}