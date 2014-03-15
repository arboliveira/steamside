package br.com.arbo.steamside.library;

import static br.com.arbo.steamside.steam.client.localfiles.sharedconfig.Factory_sharedconfig_vdf_ForExamples.from_Dir_userid;
import br.com.arbo.steamside.apps.AppsHome;
import br.com.arbo.steamside.steam.client.localfiles.SteamDirectory_ForExamples;
import br.com.arbo.steamside.steam.client.localfiles.appcache.File_appinfo_vdf;
import br.com.arbo.steamside.steam.client.localfiles.digest.Digester;
import br.com.arbo.steamside.steam.client.localfiles.localconfig.File_localconfig_vdf;
import br.com.arbo.steamside.steam.client.localfiles.sharedconfig.Dir_userid;
import br.com.arbo.steamside.steam.client.localfiles.sharedconfig.File_sharedconfig_vdf;
import br.com.arbo.steamside.steam.client.localfiles.steamlocation.SteamLocation;

public class Library_ForExamples {

	public static Library fromSteamPhysicalFiles() {
		SteamLocation steamDir =
				SteamDirectory_ForExamples.fromSteamPhysicalFiles();

		Dir_userid dir_userid = from_Dir_userid();

		Digester digester = new Digester(
				new File_appinfo_vdf(steamDir),
				new File_localconfig_vdf(dir_userid),
				new File_sharedconfig_vdf(dir_userid)
				);

		final AppsHome appsHome = digester.digest();
		return new LibraryImpl(() -> appsHome);
	}

}
