package br.com.arbo.steamside.steam.client.localfiles.localconfig;

import java.io.File;
import java.io.IOException;

import br.com.arbo.steamside.steam.client.localfiles.SteamDirectory_ForExamples;
import br.com.arbo.steamside.steam.client.localfiles.sharedconfig.Dir_userdata;
import br.com.arbo.steamside.steam.client.localfiles.sharedconfig.Dir_userid;
import br.com.arbo.steamside.vdf.DumpVdfStructure;

public final class ExampleDumpVdfStructureFrom_localconfig_vdf {

	public static void main(final String[] args) throws IOException {
		@SuppressWarnings("unused")
		final File from = from_localconfig_vdf();
		DumpVdfStructure.dump(from);
	}

	private static File from_localconfig_vdf() {
		final File_localconfig_vdf vdf = new File_localconfig_vdf(
				from_Dir_userid());
		return vdf.localconfig_vdf();
	}

	private static Dir_userid from_Dir_userid() {
		return new Dir_userid(
				new Dir_userdata(
						SteamDirectory_ForExamples
								.fromSteamPhysicalFiles()));
	}

}