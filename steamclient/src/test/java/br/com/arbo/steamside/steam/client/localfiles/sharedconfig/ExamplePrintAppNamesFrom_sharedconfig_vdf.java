package br.com.arbo.steamside.steam.client.localfiles.sharedconfig;

import br.com.arbo.steamside.steam.client.localfiles.appcache.File_appinfo_vdf;
import br.com.arbo.steamside.steam.client.localfiles.appcache.inmemory.Data_appinfo_vdf;
import br.com.arbo.steamside.steam.client.localfiles.appcache.inmemory.InMemory_appinfo_vdf;
import br.com.arbo.steamside.steam.client.localfiles.appcache.inmemory.SysoutAppInfoLine;
import br.com.arbo.steamside.steam.client.localfiles.steamlocation.SteamLocations;

class ExamplePrintAppNamesFrom_sharedconfig_vdf {

	public static void main(final String[] args) {
		final Data_appinfo_vdf appnameFactory = newAppNameFactory();

		final SysoutAppInfoLine dump = new SysoutAppInfoLine(appnameFactory);

		final DataFactory_sharedconfig_vdf vdf =
				Factory_sharedconfig_vdf_ForExamples
						.fromSteamPhysicalFiles();

		vdf.data().apps().streamAppId().map(
				appid -> dump.toInfo(appid)
				).forEach(
						System.out::println
				);
	}

	private static Data_appinfo_vdf newAppNameFactory() {
		return new InMemory_appinfo_vdf(new File_appinfo_vdf(
				SteamLocations
						.fromSteamPhysicalFiles()));
	}

}
