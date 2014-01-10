package br.com.arbo.steamside.vdf;

import java.io.File;
import java.io.IOException;

import org.apache.commons.io.FileUtils;

import br.com.arbo.steamside.steam.client.localfiles.SteamDirectory_ForExamples;
import br.com.arbo.steamside.steam.client.localfiles.localconfig.File_localconfig_vdf;
import br.com.arbo.steamside.steam.client.localfiles.sharedconfig.Dir_userdata;
import br.com.arbo.steamside.steam.client.localfiles.sharedconfig.Dir_userid;

public final class ExampleDumpVdfStructure {

	private final File from;

	public ExampleDumpVdfStructure(final File from) {
		this.from = from;
	}

	public static void main(final String[] args) throws IOException {
		File_localconfig_vdf vdf =
				new File_localconfig_vdf(
						new Dir_userid(
								new Dir_userdata(
										SteamDirectory_ForExamples
												.fromSteamPhysicalFiles())));
		final File from = vdf.localconfig_vdf();
		//File_sharedconfig_vdf.sharedconfig_vdf();
		new ExampleDumpVdfStructure(from).dump();
	}

	private void dump() throws IOException {
		final String text = FileUtils.readFileToString(from);
		final Vdf vdfImpl = new Vdf(text);
		vdfImpl.root().accept(new DumpVdfStructure());
	}

}