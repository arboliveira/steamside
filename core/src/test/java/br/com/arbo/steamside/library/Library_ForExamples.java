package br.com.arbo.steamside.library;

import static br.com.arbo.steamside.steam.client.localfiles.sharedconfig.Factory_sharedconfig_vdf_ForExamples.from_Dir_userid;
import br.com.arbo.steamside.apps.AppsHome;
import br.com.arbo.steamside.apps.AppsHomeBox;
import br.com.arbo.steamside.steam.client.localfiles.SteamDirectory_ForExamples;
import br.com.arbo.steamside.steam.client.localfiles.appcache.File_appinfo_vdf;
import br.com.arbo.steamside.steam.client.localfiles.localconfig.File_localconfig_vdf;
import br.com.arbo.steamside.steam.client.localfiles.monitoring.Digester;
import br.com.arbo.steamside.steam.client.localfiles.sharedconfig.Dir_userid;
import br.com.arbo.steamside.steam.client.localfiles.sharedconfig.File_sharedconfig_vdf;

public class Library_ForExamples {

	public static Library fromSteamPhysicalFiles() {
		File_appinfo_vdf appinfo =
				new File_appinfo_vdf(
						SteamDirectory_ForExamples.fromSteamPhysicalFiles());

		Dir_userid dir_userid = from_Dir_userid();
		File_sharedconfig_vdf sharedconfig =
				new File_sharedconfig_vdf(dir_userid);
		File_localconfig_vdf localconfig =
				new File_localconfig_vdf(dir_userid);

		Digester digester = new Digester(appinfo, localconfig, sharedconfig);

		final AppsHome home;
		try {
			home = digester.digest();
		} finally {
			digester.stop();
		}
		final AppsHomeBox box = new AppsHomeBox();
		box.set(home);
		return new LibraryImpl(box);
	}

}
