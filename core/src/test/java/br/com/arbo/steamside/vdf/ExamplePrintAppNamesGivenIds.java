package br.com.arbo.steamside.vdf;

import java.util.Arrays;

import br.com.arbo.steamside.steam.client.localfiles.SteamDirectory_ForExamples;
import br.com.arbo.steamside.steam.client.localfiles.appcache.File_appinfo_vdf;
import br.com.arbo.steamside.steam.client.localfiles.appcache.inmemory.Data_appinfo_vdf;
import br.com.arbo.steamside.steam.client.localfiles.appcache.inmemory.InMemory_appinfo_vdf;
import br.com.arbo.steamside.steam.client.localfiles.appcache.inmemory.SysoutAppInfoLine;
import br.com.arbo.steamside.types.AppId;

class ExamplePrintAppNamesGivenIds {

	public static void main(final String[] args) {
		Data_appinfo_vdf appinfo = new InMemory_appinfo_vdf(
				new File_appinfo_vdf(
						SteamDirectory_ForExamples
								.fromSteamPhysicalFiles()));

		final SysoutAppInfoLine dump = new SysoutAppInfoLine(appinfo);

		Arrays.asList(
				"22000", "9050", "12800", "10150", "35460"
				).stream().map(
						appid -> dump.toInfo(new AppId(appid))
				).
				forEach(
						System.out::println
				);
	}
}
