package br.com.arbo.steamside.steam.client.rungame;

import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.Semaphore;
import java.util.concurrent.TimeUnit;

import org.apache.commons.lang3.Validate;

import br.com.arbo.opersys.processes.seek.Criteria;
import br.com.arbo.opersys.processes.seek.ProcessSeeker;
import br.com.arbo.opersys.processes.seek.ProcessSeekerFactory;

class WaitForExecutable {

	WaitForExecutable(final String exe,
			ScheduledExecutorServiceFactory executorFactory)
	{
		Validate.notNull(exe);
		criteria = new Criteria();
		criteria.executable = exe;
		executor = executorFactory
				.newScheduledExecutorService(
				"Seek executable: " + exe);
	}

	void waitFor()
	{
		goSeekInAnotherThread();
		waitUntilItsUp();
		stopSeeking();
	}

	private void goSeekInAnotherThread()
	{
		this.future = executor.scheduleWithFixedDelay(
				this::seek, 0, 1, TimeUnit.SECONDS);
	}

	private void seek()
	{
		if (!seeker.seek(criteria)) return;
		seeking.release();
		future.cancel(false);
	}

	private void stopSeeking()
	{
		executor.shutdown();
	}

	private void waitUntilItsUp() throws Timeout
	{
		try {
			if (!seeking.tryAcquire(2, TimeUnit.MINUTES)) throw new Timeout();
		}
		catch (final InterruptedException e) {
			throw new RuntimeException(e);
		}
	}

	private final ProcessSeeker seeker = ProcessSeekerFactory.build();
	private final Criteria criteria;
	private final Semaphore seeking = new Semaphore(0);
	private final ScheduledExecutorService executor;
	private ScheduledFuture< ? > future;

}