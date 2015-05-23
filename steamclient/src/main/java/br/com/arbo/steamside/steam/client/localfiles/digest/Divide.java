package br.com.arbo.steamside.steam.client.localfiles.digest;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Future;

import br.com.arbo.steamside.steam.client.apps.AppsHome;
import br.com.arbo.steamside.steam.client.localfiles.appcache.inmemory.Data_appinfo_vdf;
import br.com.arbo.steamside.steam.client.localfiles.localconfig.Data_localconfig_vdf;
import br.com.arbo.steamside.steam.client.localfiles.sharedconfig.Data_sharedconfig_vdf;

class Divide {

	Divide(Callable<Data_appinfo_vdf> digest_appinfo_vdf,
		Callable<Data_localconfig_vdf> digest_localconfig_vdf,
		Callable<Data_sharedconfig_vdf> digest_sharedconfig_vdf,
		ExecutorService divideExecutor)
	{
		f_localconfig =
			divideExecutor.submit(digest_localconfig_vdf);
		f_sharedconfig =
			divideExecutor.submit(digest_sharedconfig_vdf);
		f_appinfo =
			divideExecutor.submit(digest_appinfo_vdf);
	}

	Future<AppsHome> conquer(ExecutorService conquerExecutor)
	{
		return conquerExecutor.submit(() -> {
			return new AppsHomeFromLocalFiles(
				f_appinfo.get(),
				f_localconfig.get(),
				f_sharedconfig.get())
					.combine();
		});

	}

	private final Future<Data_appinfo_vdf> f_appinfo;
	private final Future<Data_localconfig_vdf> f_localconfig;
	private final Future<Data_sharedconfig_vdf> f_sharedconfig;

}
