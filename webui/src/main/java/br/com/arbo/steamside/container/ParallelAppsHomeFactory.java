package br.com.arbo.steamside.container;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import javax.inject.Inject;

import br.com.arbo.java.util.concurrent.DaemonThreadFactory;
import br.com.arbo.java.util.concurrent.Parallel;
import br.com.arbo.steamside.apps.AppsHome;
import br.com.arbo.steamside.apps.AppsHomeFactory;
import br.com.arbo.steamside.steam.client.localfiles.appcache.File_appinfo_vdf;
import br.com.arbo.steamside.steam.client.localfiles.digest.Digester;
import br.com.arbo.steamside.steam.client.localfiles.localconfig.File_localconfig_vdf;
import br.com.arbo.steamside.steam.client.localfiles.sharedconfig.File_sharedconfig_vdf;

public class ParallelAppsHomeFactory implements AppsHomeFactory {

	@Inject
	public ParallelAppsHomeFactory(File_appinfo_vdf file_appinfo_vdf,
			File_localconfig_vdf file_localconfig_vdf,
			File_sharedconfig_vdf file_sharedconfig_vdf)
	{
		this.file_appinfo_vdf = file_appinfo_vdf;
		this.file_localconfig_vdf = file_localconfig_vdf;
		this.file_sharedconfig_vdf = file_sharedconfig_vdf;
		this.parallel = new Parallel<AppsHome>(this::digest);
	}

	@Override
	public AppsHome get()
	{
		return parallel.get();
	}

	public void start()
	{
		parallel.start();
	}

	public void stop()
	{
		parallel.stop();
	}

	public void submit()
	{
		parallel.submit();
	}

	AppsHome digest()
	{
		ExecutorService executor = newThreeDaemonThreads();
		try {
			Digester digester = new Digester(
					file_appinfo_vdf, file_localconfig_vdf,
					file_sharedconfig_vdf, executor);
			return digester.digest();
		}
		finally {
			executor.shutdown();
		}
	}

	private ExecutorService newThreeDaemonThreads()
	{
		return Executors.newFixedThreadPool(
				3, DaemonThreadFactory.forClass(this.getClass()));
	}

	private final File_appinfo_vdf file_appinfo_vdf;

	private final File_localconfig_vdf file_localconfig_vdf;

	private final File_sharedconfig_vdf file_sharedconfig_vdf;

	private final Parallel<AppsHome> parallel;

}
