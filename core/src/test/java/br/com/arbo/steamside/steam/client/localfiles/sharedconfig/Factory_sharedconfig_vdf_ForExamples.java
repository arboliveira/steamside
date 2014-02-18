package br.com.arbo.steamside.steam.client.localfiles.sharedconfig;

import br.com.arbo.steamside.steam.client.localfiles.SteamDirectory_ForExamples;

public class Factory_sharedconfig_vdf_ForExamples {

	public static DataFactory_sharedconfig_vdf fromSteamPhysicalFiles() {
		return new Factory_sharedconfig_vdf(
				new File_sharedconfig_vdf(from_Dir_userid()));
	}

	public static Dir_userid from_Dir_userid() {
		return new Dir_userid(new Dir_userdata(
				SteamDirectory_ForExamples
						.fromSteamPhysicalFiles()));
	}

}
