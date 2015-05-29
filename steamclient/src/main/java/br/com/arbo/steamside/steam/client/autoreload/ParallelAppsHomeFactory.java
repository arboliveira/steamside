package br.com.arbo.steamside.steam.client.autoreload;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.Semaphore;
import java.util.concurrent.ThreadFactory;

import javax.inject.Inject;

import org.apache.commons.lang3.concurrent.BasicThreadFactory;

import br.com.arbo.steamside.steam.client.apps.AppsHome;
import br.com.arbo.steamside.steam.client.apps.AppsHomeFactory;
import br.com.arbo.steamside.steam.client.localfiles.appcache.File_appinfo_vdf;
import br.com.arbo.steamside.steam.client.localfiles.digest.Digester;
import br.com.arbo.steamside.steam.client.localfiles.localconfig.File_localconfig_vdf;
import br.com.arbo.steamside.steam.client.localfiles.monitoring.Monitor;
import br.com.arbo.steamside.steam.client.localfiles.sharedconfig.File_sharedconfig_vdf;

public class ParallelAppsHomeFactory implements AppsHomeFactory
{

	private static ExecutorService newFixedThreadPool(
		int nThreads, ThreadFactory threadFactory)
	{
		return Executors.newFixedThreadPool(nThreads, threadFactory);
	}

	@Inject
	public ParallelAppsHomeFactory(
		File_appinfo_vdf file_appinfo_vdf,
		File_localconfig_vdf file_localconfig_vdf,
		File_sharedconfig_vdf file_sharedconfig_vdf)
	{
		this.file_appinfo_vdf = file_appinfo_vdf;
		this.file_localconfig_vdf = file_localconfig_vdf;
		this.file_sharedconfig_vdf = file_sharedconfig_vdf;

		this.monitor = new Monitor(
			file_localconfig_vdf, file_sharedconfig_vdf, file_appinfo_vdf,
			this::reload);

		ThreadFactory threadFactory = newDaemonThreadFactory();
		divideExecutor = newFixedThreadPool(3, threadFactory);
		conquerExecutor = newFixedThreadPool(1, threadFactory);
	}

	@Override
	public AppsHome get()
	{
		try
		{
			waitUntilAvailable();
			return appsHomeFuture.get();
		}
		catch (final InterruptedException e)
		{
			throw new RuntimeException(e);
		}
		catch (final ExecutionException e)
		{
			final Throwable cause = e.getCause();
			if (cause instanceof RuntimeException)
				throw (RuntimeException) cause;
			throw new RuntimeException(e);
		}
	}

	public void start()
	{
		reload();
		monitor.start();
	}

	public void stop()
	{
		monitor.stop();
		divideExecutor.shutdown();
		conquerExecutor.shutdown();
	}

	void reload()
	{
		this.appsHomeFuture = this.digest();
		available.release();
	}

	private Future<AppsHome> digest()
	{
		return new Digester(
			file_appinfo_vdf,
			file_localconfig_vdf,
			file_sharedconfig_vdf,
			divideExecutor,
			conquerExecutor)
		.digest();
	}

	private BasicThreadFactory newDaemonThreadFactory()
	{
		return new BasicThreadFactory.Builder()
			.daemon(true)
			.namingPattern(this.getClass().getSimpleName() + "-%d")
			.build();
	}

	private void waitUntilAvailable() throws InterruptedException
	{
		available.acquire();
		available.release();
	}

	private final File_appinfo_vdf file_appinfo_vdf;
	private final File_localconfig_vdf file_localconfig_vdf;
	private final File_sharedconfig_vdf file_sharedconfig_vdf;
	private final Monitor monitor;
	private final ExecutorService divideExecutor;
	private final ExecutorService conquerExecutor;
	private Future<AppsHome> appsHomeFuture;
	private final Semaphore available = new Semaphore(0);

}