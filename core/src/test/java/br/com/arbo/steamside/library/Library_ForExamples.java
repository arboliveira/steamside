package br.com.arbo.steamside.library;

import br.com.arbo.steamside.steam.client.localfiles.sharedconfig.Factory_sharedconfig_vdf_ForExamples;

public class Library_ForExamples {

	public static Library fromSteamPhysicalFiles() {
		return new LibraryImpl(
				Factory_sharedconfig_vdf_ForExamples.fromSteamPhysicalFiles());
	}

}
