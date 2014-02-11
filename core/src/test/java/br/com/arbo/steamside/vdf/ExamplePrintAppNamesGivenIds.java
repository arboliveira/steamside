package br.com.arbo.steamside.vdf;

import java.util.Arrays;
import java.util.List;

import org.eclipse.jdt.annotation.NonNull;

import br.com.arbo.steamside.steam.client.localfiles.SteamDirectory_ForExamples;
import br.com.arbo.steamside.steam.client.localfiles.appcache.AppNameFromLocalFiles;
import br.com.arbo.steamside.steam.client.localfiles.appcache.File_appinfo_vdf;
import br.com.arbo.steamside.steam.client.localfiles.appcache.inmemory.InMemory_appinfo_vdf;
import br.com.arbo.steamside.steam.client.localfiles.appcache.inmemory.SysoutAppInfoLine;
import br.com.arbo.steamside.types.AppId;

class ExamplePrintAppNamesGivenIds {

	@SuppressWarnings("null")
	public static void main(final String[] args) {
		final AppNameFromLocalFiles appnameFactory =
				new AppNameFromLocalFiles(new InMemory_appinfo_vdf(
						new File_appinfo_vdf(
								SteamDirectory_ForExamples
										.fromSteamPhysicalFiles())));
		final SysoutAppInfoLine dump = new SysoutAppInfoLine(appnameFactory);
		final List<String> ids = Arrays.asList(
				"22000", "9050", "12800", "10150", "35460");
		for (@NonNull
		final String appid : ids)
			System.out.println(dump.toInfo(new AppId(appid)));
	}
}
