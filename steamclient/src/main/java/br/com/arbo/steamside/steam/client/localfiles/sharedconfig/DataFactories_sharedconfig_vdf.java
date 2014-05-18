package br.com.arbo.steamside.steam.client.localfiles.sharedconfig;


public class DataFactories_sharedconfig_vdf {

	public static DataFactory_sharedconfig_vdf fromSteamPhysicalFiles() {
		return new Factory_sharedconfig_vdf(
				new File_sharedconfig_vdf(Dirs_userid.from_Dir_userid()));
	}

}
