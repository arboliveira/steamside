package br.com.arbo.steamside.data;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.Semaphore;

import javax.inject.Inject;

import org.apache.commons.lang3.concurrent.BasicThreadFactory;

import br.com.arbo.steamside.collections.CollectionI;
import br.com.arbo.steamside.collections.CollectionsData;
import br.com.arbo.steamside.data.copy.CopyAllSteamCategories;
import br.com.arbo.steamside.steam.client.library.Library;
import br.com.arbo.steamside.types.CollectionName;

public class SteamsideDataExecutorImpl implements SteamsideDataExecutor
{

	@Inject
	public SteamsideDataExecutorImpl(
		SteamsideData steamsideData,
		Library library)
	{
		this.steamsideData = steamsideData;
		this.library = library;

		this.executorService =
			Executors.newCachedThreadPool(
				new BasicThreadFactory.Builder()
				.namingPattern(this.getClass().getSimpleName() + "-%d")
				.build());
	}

	@Override
	public void enqueueCopyAllSteamCategories()
	{
		submit(this::copyAllSteamCategories);
	}

	public void shutdown()
	{
		executorService.shutdown();
	}

	public void start()
	{
		available.release();
	}

	public <T> Future<T> submit(Callable<T> task)
	{
		return executorService.submit(() -> {
			waitUntilAvailable();
			return task.call();
		});
	}

	public Future<Runnable> submit(Runnable task)
	{
		return executorService.submit(() -> {
			waitUntilAvailable();
			task.run();
			return task;
		});
	}

	private void copyAllSteamCategories()
	{
		new CopyAllSteamCategories(steamsideData.tags(), library).execute();

		CollectionsData c = steamsideData.collections();
		CollectionI in = c.find(new CollectionName("favorite"));
		c.favorite(in);
	}

	private void waitUntilAvailable() throws InterruptedException
	{
		available.acquire();
		available.release();
	}

	private final Semaphore available = new Semaphore(0);

	private final ExecutorService executorService;

	private final Library library;

	private final SteamsideData steamsideData;
}
