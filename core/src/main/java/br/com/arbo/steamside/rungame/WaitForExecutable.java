package br.com.arbo.steamside.rungame;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.Semaphore;
import java.util.concurrent.TimeUnit;

import org.apache.commons.lang3.Validate;

import br.com.arbo.java.util.concurrent.DaemonThreadFactory;
import br.com.arbo.processes.seek.Criteria;
import br.com.arbo.processes.seek.ProcessSeeker;
import br.com.arbo.processes.seek.ProcessSeekerFactory;

class WaitForExecutable {

	void waitFor() {
		goSeekInAnotherThread();
		waitUntilItsUp();
		stopSeeking();
	}

	private void goSeekInAnotherThread() {
		this.future = executor.scheduleWithFixedDelay(
				this::seek, 0, 1, TimeUnit.SECONDS);
	}

	private void waitUntilItsUp() throws Timeout {
		try {
			if (!seeking.tryAcquire(2, TimeUnit.MINUTES)) throw new Timeout();
		} catch (final InterruptedException e) {
			throw new RuntimeException(e);
		}
	}

	private void stopSeeking() {
		executor.shutdown();
	}

	private void seek() {
		if (!seeker.seek(criteria)) return;
		seeking.release();
		future.cancel(false);
	}

	WaitForExecutable(final String exe) {
		Validate.notNull(exe);
		criteria = new Criteria();
		criteria.executable = exe;
		executor = Executors.newScheduledThreadPool(
				1, DaemonThreadFactory.withPrefix("Seek executable: " + exe));
	}

	private final ProcessSeeker seeker = ProcessSeekerFactory.build();
	private final Criteria criteria;
	private final Semaphore seeking = new Semaphore(0);
	private final ScheduledExecutorService executor;
	private ScheduledFuture< ? > future;

}