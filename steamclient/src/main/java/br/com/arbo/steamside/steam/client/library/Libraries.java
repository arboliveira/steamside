package br.com.arbo.steamside.steam.client.library;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import br.com.arbo.steamside.steam.client.apps.AppsHome;
import br.com.arbo.steamside.steam.client.apps.AppsHomeFactory;
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
		return new LibraryImpl(appsHomeFactory());
	}

	private static AppsHome appsHome()
		throws InterruptedException, ExecutionException
	{
		ExecutorService executorService = Executors.newSingleThreadExecutor();
		try
		{
			Future<AppsHome> digest = digest(executorService);
			return digest.get();
		}
		finally
		{
			executorService.shutdown();
		}
	}

	private static AppsHomeFactory appsHomeFactory()
	{
		try
		{
			AppsHome appsHome = appsHome();
			return () -> appsHome;
		}
		catch (InterruptedException | ExecutionException e)
		{
			throw new RuntimeException(e);
		}
	}

	private static Future<AppsHome> digest(ExecutorService executorService)
	{
		SteamLocation steamDir =
			SteamLocations.fromSteamPhysicalFiles();

		Dir_userid dir_userid = Dirs_userid.fromSteamPhysicalFiles();

		Digester digester = new Digester(
			new File_appinfo_vdf(steamDir),
			new File_localconfig_vdf(dir_userid),
			new File_sharedconfig_vdf(dir_userid),
			executorService, executorService);

		return digester.digest();
	}

}
