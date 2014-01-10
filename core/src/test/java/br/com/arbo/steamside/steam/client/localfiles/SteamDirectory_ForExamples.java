package br.com.arbo.steamside.steam.client.localfiles;

import br.com.arbo.org.apache.commons.lang3.FromSystemUtils;


public class SteamDirectory_ForExamples {

	public static SteamDirectory fromSteamPhysicalFiles() {
		return new SteamDirectory(
				new FromSystemUtils());
	}

}
