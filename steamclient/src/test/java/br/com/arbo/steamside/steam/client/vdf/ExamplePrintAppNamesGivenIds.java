package br.com.arbo.steamside.steam.client.vdf;

import java.util.Arrays;

import br.com.arbo.steamside.steam.client.localfiles.appcache.File_appinfo_vdf;
import br.com.arbo.steamside.steam.client.localfiles.appcache.inmemory.AppInfoAppNameType;
import br.com.arbo.steamside.steam.client.localfiles.appcache.inmemory.Data_appinfo_vdf;
import br.com.arbo.steamside.steam.client.localfiles.appcache.inmemory.InMemory_appinfo_vdf;
import br.com.arbo.steamside.steam.client.localfiles.appcache.inmemory.SysoutAppInfoLine;
import br.com.arbo.steamside.steam.client.localfiles.steamlocation.SteamLocations;
import br.com.arbo.steamside.steam.client.types.AppId;

class ExamplePrintAppNamesGivenIds {

	public static void main(final String[] args)
	{
		Data_appinfo_vdf appinfo = new InMemory_appinfo_vdf(
				new File_appinfo_vdf(
						SteamLocations
								.fromSteamPhysicalFiles()));

		Arrays.asList(
				"22000", "9050", "12800", "10150", "35460"
				)
				.stream()
				.map(
						appid -> SysoutAppInfoLine.toInfo(
								new AppInfoAppNameType(
										new AppId(appid), appinfo))
				).parallel().forEach(
						System.out::println
				);
	}
}
