package br.com.arbo.steamside.rungame;

import java.util.concurrent.Semaphore;
import java.util.concurrent.TimeUnit;

import org.apache.commons.lang3.Validate;

import br.com.arbo.processes.seek.Criteria;
import br.com.arbo.processes.seek.ProcessSeeker;
import br.com.arbo.processes.seek.ProcessSeekerFactory;

class WaitForExecutable {

	void waitFor() {
		final Thread t = seekInAnotherThread();
		try {
			waitUntilItsUp();
		} finally {
			t.interrupt();
		}
	}

	private Thread seekInAnotherThread() {
		final Thread t = new Thread(
				this::againAndAgain,
				"Seek executable: " + exe);
		t.setDaemon(true);
		t.start();
		return t;
	}

	private void againAndAgain() {
		final boolean found = seekExecutableAgainAndAgain(criteria);
		if (found) seeking.release();
	}

	private void waitUntilItsUp() throws Timeout {
		try {
			if (!seeking.tryAcquire(2, TimeUnit.MINUTES)) throw new Timeout();
		} catch (final InterruptedException e) {
			throw new RuntimeException(e);
		}
	}

	private static boolean seekExecutableAgainAndAgain(final Criteria criteria) {
		final ProcessSeeker seeker = ProcessSeekerFactory.build();
		while (true) {
			final boolean found = seeker.seek(criteria);
			if (found) return true;
			try {
				Thread.sleep(1000);
			} catch (final InterruptedException e) {
				return false;
			}
		}
	}

	WaitForExecutable(final String exe) {
		Validate.notNull(exe);
		this.exe = exe;
		criteria = new Criteria();
		criteria.executable = exe;
	}

	private final String exe;
	private final Semaphore seeking = new Semaphore(0);
	private final Criteria criteria;
}