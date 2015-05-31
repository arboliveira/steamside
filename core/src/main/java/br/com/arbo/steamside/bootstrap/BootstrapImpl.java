package br.com.arbo.steamside.bootstrap;

import java.util.ArrayList;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.Semaphore;

import org.apache.commons.lang3.concurrent.BasicThreadFactory;

public class BootstrapImpl implements Bootstrap
{

	public BootstrapImpl()
	{
		this.whenWired =
			Executors.newCachedThreadPool(
				new BasicThreadFactory.Builder()
				.namingPattern(this.getClass().getSimpleName() + "-%d")
				.build());
	}

	@Override
	public void addObserver(EventObserver observer)
	{
		observers.add(observer);
	}

	@Override
	public void fireEvent(Event event)
	{
		for (EventObserver observer : observers)
			observer.onEvent(event);
	}

	public void start()
	{
		available.release();
	}

	public void stop()
	{
		whenWired.shutdown();
	}

	@Override
	public <T> Future<T> whenWired(Callable<T> task)
	{
		return whenWired.submit(() -> {
			waitUntilAvailable();
			return task.call();
		});
	}

	@Override
	public Future<Bootstrap> whenWired(Runnable task)
	{
		return whenWired.submit(() -> {
			waitUntilAvailable();
			task.run();
			return this;
		});
	}

	private void waitUntilAvailable() throws InterruptedException
	{
		available.acquire();
		available.release();
	}

	private final Semaphore available = new Semaphore(0);
	private final ArrayList<EventObserver> observers = new ArrayList<>(1);
	private final ExecutorService whenWired;
}
