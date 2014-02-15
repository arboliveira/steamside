package br.com.arbo.steamside.steam.client.localfiles.sharedconfig;

import java.io.File;

import br.com.arbo.steamside.steam.client.localfiles.SteamDirectory_ForExamples;
import br.com.arbo.steamside.vdf.DumpVdfStructure;

public final class ExampleDumpVdfStructureFrom_sharedconfig_vdf {

	public static void main(final String[] args) {
		final File from = from_sharedconfig_vdf();
		DumpVdfStructure.dump(from);
	}

	private static File from_sharedconfig_vdf() {
		final File_sharedconfig_vdf vdf = new File_sharedconfig_vdf(
				from_Dir_userid());
		return vdf.sharedconfig_vdf();
	}

	private static Dir_userid from_Dir_userid() {
		return new Dir_userid(
				new Dir_userdata(
						SteamDirectory_ForExamples
								.fromSteamPhysicalFiles()));
	}

}