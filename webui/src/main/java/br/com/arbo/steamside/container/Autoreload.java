package br.com.arbo.steamside.container;

import javax.inject.Inject;

import org.springframework.context.SmartLifecycle;

import br.com.arbo.steamside.steam.client.localfiles.monitoring.AutoreloadingAppsHomeFactory;
import br.com.arbo.steamside.steam.client.localfiles.monitoring.Monitor;

public class Autoreload implements SmartLifecycle {

	@Inject
	private AutoreloadingAppsHomeFactory appsHome;

	@Inject
	private Monitor monitor;

	@Override
	public void start() {
		monitor.start();
		running = true;
	}

	@Override
	public void stop() {
		running = false;
		monitor.stop();
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
	public void stop(final Runnable callback) {
		stop();
		callback.run();
	}

	private boolean running;

}
