package br.com.arbo.steamside.steam.client.localfiles.monitoring;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import javax.inject.Inject;

import org.springframework.context.SmartLifecycle;

import br.com.arbo.steamside.apps.AppsHome;
import br.com.arbo.steamside.apps.AppsHomeBox;

public class DigestOnChange
		implements ChangeListener, SmartLifecycle {

	@Inject
	private Digester digester;

	@Inject
	private AppsHomeBox homeBox;

	private boolean running;

	@Override
	public void fileChanged() {
		digestInParallel();
	}

	@Override
	public void start() {
		digestInParallel();
		running = true;
	}

	@Override
	public void stop() {
		running = false;
		digestExec.shutdown();
	}

	@Override
	public boolean isRunning() {
		return running;
	}

	@Override
	public int getPhase() {
		return 1;
	}

	@Override
	public boolean isAutoStartup() {
		return true;
	}

	@Override
	public void stop(Runnable callback) {
		stop();
		callback.run();
	}

	private void digestInParallel() {
		digestExec.execute(new Digest());
	}

	class Digest implements Runnable {

		@Override
		public void run() {
			digestXT();
		}
	}

	void digestXT() {
		AppsHome home = digester.digest();
		homeBox.set(home);
	}

	private final ExecutorService digestExec = Executors
			.newSingleThreadExecutor();
}
