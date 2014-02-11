package br.com.arbo.steamside.steam.client.localfiles.sharedconfig;

import br.com.arbo.steamside.steam.client.localfiles.SteamDirectory_ForExamples;
import br.com.arbo.steamside.steam.client.localfiles.appcache.AppNameFromLocalFiles;
import br.com.arbo.steamside.steam.client.localfiles.appcache.File_appinfo_vdf;
import br.com.arbo.steamside.steam.client.localfiles.appcache.inmemory.InMemory_appinfo_vdf;
import br.com.arbo.steamside.steam.client.localfiles.appcache.inmemory.SysoutAppInfoLine;
import br.com.arbo.steamside.steam.store.AppNameFactory;
import br.com.arbo.steamside.web.AppNameFromSteamStore;

class ExamplePrintAppNamesFrom_sharedconfig_vdf {

	static final boolean web = false;

	public static void main(final String[] args) {
		final AppNameFactory appnameFactory =
				ExamplePrintAppNamesFrom_sharedconfig_vdf.newAppNameFactory();
		final SysoutAppInfoLine dump =
				new SysoutAppInfoLine(appnameFactory);
		new Dump_sharedconfig_vdf_Apps(dump,
				Factory_sharedconfig_vdf_ForExamples.fromSteamPhysicalFiles())
				.dump();
	}

	private static AppNameFactory newAppNameFactory() {
		if (web) return new AppNameFromSteamStore();
		return new AppNameFromLocalFiles(
				new InMemory_appinfo_vdf(new File_appinfo_vdf(
						SteamDirectory_ForExamples
								.fromSteamPhysicalFiles())));
	}

}
