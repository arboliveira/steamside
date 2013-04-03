package br.com.arbo.steamside.vdf;

import br.com.arbo.steamside.steam.client.localfiles.appcache.AppNameFromLocalFiles;
import br.com.arbo.steamside.steam.client.localfiles.appcache.InMemory_appinfo_vdf;
import br.com.arbo.steamside.steam.store.AppNameFactory;
import br.com.arbo.steamside.web.AppNameFromSteamStore;

class ExamplePrintAppNamesFromSharedconfigVdf {

	static final boolean web = false;

	public static void main(final String[] args) {
		final AppNameFactory appnameFactory =
				ExamplePrintAppNamesFromSharedconfigVdf.newAppNameFactory();
		final SysoutAppInfoLine dump =
				new SysoutAppInfoLine(appnameFactory);
		new DumpSharedconfigVdfApps(dump).dump();
	}

	private static AppNameFactory newAppNameFactory() {
		if (web) return new AppNameFromSteamStore();
		return new AppNameFromLocalFiles(
				new InMemory_appinfo_vdf());
	}

}
