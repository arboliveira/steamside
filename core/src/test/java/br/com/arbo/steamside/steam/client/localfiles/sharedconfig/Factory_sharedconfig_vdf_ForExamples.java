package br.com.arbo.steamside.steam.client.localfiles.sharedconfig;

import br.com.arbo.steamside.steam.client.localfiles.SteamDirectory_ForExamples;


public class Factory_sharedconfig_vdf_ForExamples {

	public static Factory_sharedconfig_vdf fromSteamPhysicalFiles() {
		return new Factory_sharedconfig_vdf(
				new File_sharedconfig_vdf(new Dir_userid(new Dir_userdata(
						SteamDirectory_ForExamples
								.fromSteamPhysicalFiles()))));
	}

}
