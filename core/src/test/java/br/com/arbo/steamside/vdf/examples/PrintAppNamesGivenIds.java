package br.com.arbo.steamside.vdf.examples;

import java.util.Arrays;
import java.util.List;

import org.eclipse.jdt.annotation.NonNull;

import br.com.arbo.steamside.steam.client.localfiles.appcache.AppNameFromLocalFiles;
import br.com.arbo.steamside.steam.client.localfiles.appcache.InMemory_appinfo_vdf;
import br.com.arbo.steamside.types.AppId;

public class PrintAppNamesGivenIds {

	@SuppressWarnings("null")
	public static void main(final String[] args) {
		final AppNameFromLocalFiles appnameFactory =
				new AppNameFromLocalFiles(new InMemory_appinfo_vdf());
		final SysoutAppInfoLine dump = new SysoutAppInfoLine(appnameFactory);
		final List<String> ids = Arrays.asList(
				"22000", "9050", "12800", "10150", "35460");
		for (@NonNull
		final String appid : ids)
			dump.sysout(new AppId(appid));
	}
}
