package br.com.arbo.steamside.vdf;

import java.io.File;
import java.io.IOException;

import org.apache.commons.io.FileUtils;

import br.com.arbo.steamside.steam.client.localfiles.SteamDirectory_ForExamples;
import br.com.arbo.steamside.steam.client.localfiles.localconfig.File_localconfig_vdf;
import br.com.arbo.steamside.steam.client.localfiles.sharedconfig.Dir_userdata;
import br.com.arbo.steamside.steam.client.localfiles.sharedconfig.Dir_userid;
import br.com.arbo.steamside.steam.client.localfiles.sharedconfig.File_sharedconfig_vdf;

public final class ExampleDumpVdfStructure {

	private final File from;

	public ExampleDumpVdfStructure(final File from) {
		this.from = from;
	}

	public static void main(final String[] args) throws IOException {
		@SuppressWarnings("unused")
		final File from = true ? from_localconfig_vdf()
				: from_sharedconfig_vdf();
		new ExampleDumpVdfStructure(from).dump();
	}

	private static File from_sharedconfig_vdf() {
		final File_sharedconfig_vdf vdf = new File_sharedconfig_vdf(
				from_Dir_userid());
		final File file = vdf.sharedconfig_vdf();
		return file;
	}

	private static File from_localconfig_vdf() {
		final File_localconfig_vdf vdf = new File_localconfig_vdf(
				from_Dir_userid());
		final File file = vdf.localconfig_vdf();
		return file;
	}

	private static Dir_userid from_Dir_userid() {
		return new Dir_userid(
				new Dir_userdata(
						SteamDirectory_ForExamples
								.fromSteamPhysicalFiles()));
	}

	private void dump() throws IOException {
		final String text = FileUtils.readFileToString(from);
		final Vdf vdfImpl = new Vdf(text);
		vdfImpl.root().accept(new DumpVdfStructure());
	}

}