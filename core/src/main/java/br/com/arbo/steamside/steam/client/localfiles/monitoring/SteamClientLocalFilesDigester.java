package br.com.arbo.steamside.steam.client.localfiles.monitoring;

import java.util.concurrent.Semaphore;

import org.springframework.context.Lifecycle;

public class SteamClientLocalFilesDigester
		implements Lifecycle, SteamClientLocalFilesChangeListener {

	public SteamClientLocalFilesDigester() {
		on = true;
	}

	@Override
	public void start() {
		on = true;
		digestInParallel();
		beginThreads();
	}

	@Override
	public void stop() {
		endThreads();
		on = false;
	}

	@Override
	public boolean isRunning() {
		return false;
	}

	@Override
	public void fileChanged() {
		canDigest.release();
	}

	private void digestInParallel() {

	}

	private void beginThreads() {
		// TODO Auto-generated method stub

	}

	private void endThreads() {
		// TODO Auto-generated method stub

	}

	class Digest implements Runnable {

		@Override
		public void run() {
			try {
				digestForeverXT();
			} catch (InterruptedException e) {
				throw new RuntimeException(e);
			}
		}
	}

	private final Semaphore canDigest = new Semaphore(1);
	private boolean on;

	void digestForeverXT() throws InterruptedException {
		while (on) {
			canDigest.acquire();
			canDigest.drainPermits();
			digest();
		}
	}

	private void digest() {
		// TODO Auto-generated method stub

	}
}
