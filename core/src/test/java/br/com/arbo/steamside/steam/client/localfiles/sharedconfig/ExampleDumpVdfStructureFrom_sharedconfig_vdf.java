package br.com.arbo.steamside.steam.client.localfiles.sharedconfig;

import br.com.arbo.steamside.steam.client.localfiles.SteamDirectory_ForExamples;
import br.com.arbo.steamside.vdf.DumpVdfStructure;

public final class ExampleDumpVdfStructureFrom_sharedconfig_vdf {

	public static void main(final String[] args) {
		DumpVdfStructure.dump(from_sharedconfig_vdf().sharedconfig_vdf());
	}

	private static File_sharedconfig_vdf from_sharedconfig_vdf() {
		return new File_sharedconfig_vdf(
				from_Dir_userid());
	}

	private static Dir_userid from_Dir_userid() {
		return new Dir_userid(
				new Dir_userdata(
						SteamDirectory_ForExamples
								.fromSteamPhysicalFiles()));
	}

}