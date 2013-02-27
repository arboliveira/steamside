package br.com.arbo.steamside.vdf.examples;

import java.util.Arrays;

import br.com.arbo.steamside.steam.client.localfiles.appcache.AppNameFromLocalFiles;
import br.com.arbo.steamside.steam.client.localfiles.appcache.InMemory_appinfo_vdf;
import br.com.arbo.steamside.types.AppId;

public class PrintAppNamesGivenIds {

	public static void main(final String[] args) {
		final AppNameFromLocalFiles appnameFactory =
				new AppNameFromLocalFiles(new InMemory_appinfo_vdf());
		final SysoutAppInfoLine dump = new SysoutAppInfoLine(appnameFactory);
		for (final String appid : Arrays.asList(
				"22000", "9050", "12800", "10150", "35460"))
			dump.sysout(new AppId(appid));
	}
}
