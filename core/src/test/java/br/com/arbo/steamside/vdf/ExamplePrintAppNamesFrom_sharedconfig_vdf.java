package br.com.arbo.steamside.vdf;

import br.com.arbo.org.apache.commons.lang3.FromSystemUtils;
import br.com.arbo.steamside.steam.client.localfiles.SteamDirectory;
import br.com.arbo.steamside.steam.client.localfiles.appcache.AppNameFromLocalFiles;
import br.com.arbo.steamside.steam.client.localfiles.appcache.File_appinfo_vdf;
import br.com.arbo.steamside.steam.client.localfiles.appcache.inmemory.InMemory_appinfo_vdf;
import br.com.arbo.steamside.steam.client.localfiles.sharedconfig.Dir_userdata;
import br.com.arbo.steamside.steam.client.localfiles.sharedconfig.Dir_userid;
import br.com.arbo.steamside.steam.client.localfiles.sharedconfig.Factory_sharedconfig_vdf;
import br.com.arbo.steamside.steam.client.localfiles.sharedconfig.File_sharedconfig_vdf;
import br.com.arbo.steamside.steam.store.AppNameFactory;
import br.com.arbo.steamside.web.AppNameFromSteamStore;

class ExamplePrintAppNamesFrom_sharedconfig_vdf {

	private static final SteamDirectory steamDirectory = new SteamDirectory(
			new FromSystemUtils());

	static final boolean web = false;

	public static void main(final String[] args) {
		final AppNameFactory appnameFactory =
				ExamplePrintAppNamesFrom_sharedconfig_vdf.newAppNameFactory();
		final SysoutAppInfoLine dump =
				new SysoutAppInfoLine(appnameFactory);
		new Dump_sharedconfig_vdf_Apps(dump, new Factory_sharedconfig_vdf(
				new File_sharedconfig_vdf(new Dir_userid(new Dir_userdata(
						steamDirectory))))).dump();
	}

	private static AppNameFactory newAppNameFactory() {
		if (web) return new AppNameFromSteamStore();
		return new AppNameFromLocalFiles(
				new InMemory_appinfo_vdf(new File_appinfo_vdf(
						steamDirectory)));
	}

}
