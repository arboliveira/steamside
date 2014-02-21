package br.com.arbo.steamside.steam.client.localfiles.sharedconfig;

import br.com.arbo.steamside.steam.client.localfiles.SteamDirectory_ForExamples;
import br.com.arbo.steamside.steam.client.localfiles.appcache.File_appinfo_vdf;
import br.com.arbo.steamside.steam.client.localfiles.appcache.inmemory.Data_appinfo_vdf;
import br.com.arbo.steamside.steam.client.localfiles.appcache.inmemory.InMemory_appinfo_vdf;
import br.com.arbo.steamside.steam.client.localfiles.appcache.inmemory.SysoutAppInfoLine;

class ExamplePrintAppNamesFrom_sharedconfig_vdf {

	public static void main(final String[] args) {
		final Data_appinfo_vdf appnameFactory = newAppNameFactory();
		final SysoutAppInfoLine dump = new SysoutAppInfoLine(appnameFactory);
		new Dump_sharedconfig_vdf_Apps(dump,
				Factory_sharedconfig_vdf_ForExamples.fromSteamPhysicalFiles())
				.dump();
	}

	private static Data_appinfo_vdf newAppNameFactory() {
		return new InMemory_appinfo_vdf(new File_appinfo_vdf(
				SteamDirectory_ForExamples
						.fromSteamPhysicalFiles()));
	}

}
