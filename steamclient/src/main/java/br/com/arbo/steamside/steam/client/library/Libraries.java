package br.com.arbo.steamside.steam.client.library;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import br.com.arbo.steamside.steam.client.apps.AppsHome;
import br.com.arbo.steamside.steam.client.localfiles.appcache.File_appinfo_vdf;
import br.com.arbo.steamside.steam.client.localfiles.digest.Digester;
import br.com.arbo.steamside.steam.client.localfiles.localconfig.File_localconfig_vdf;
import br.com.arbo.steamside.steam.client.localfiles.sharedconfig.Dir_userid;
import br.com.arbo.steamside.steam.client.localfiles.sharedconfig.Dirs_userid;
import br.com.arbo.steamside.steam.client.localfiles.sharedconfig.File_sharedconfig_vdf;
import br.com.arbo.steamside.steam.client.localfiles.steamlocation.SteamLocation;
import br.com.arbo.steamside.steam.client.localfiles.steamlocation.SteamLocations;

public class Libraries {

	public static Library fromSteamPhysicalFiles()
	{
		SteamLocation steamDir =
				SteamLocations.fromSteamPhysicalFiles();

		Dir_userid dir_userid = Dirs_userid.from_Dir_userid();

		final AppsHome appsHome;

		ExecutorService executorService = Executors.newSingleThreadExecutor();
		try {

			Digester digester = new Digester(
					new File_appinfo_vdf(steamDir),
					new File_localconfig_vdf(dir_userid),
					new File_sharedconfig_vdf(dir_userid),
					executorService
					);

			appsHome = digester.digest();
		}
		finally {
			executorService.shutdown();
		}

		return new LibraryImpl(() -> appsHome);
	}

}
