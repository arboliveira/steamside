package br.com.arbo.steamside.app.jetty;

import javax.inject.Inject;

import org.springframework.context.SmartLifecycle;

import br.com.arbo.steamside.container.ContainerFactory;
import br.com.arbo.steamside.steam.client.localfiles.monitoring.Monitor;
import br.com.arbo.steamside.steam.client.localfiles.monitoring.ParallelAppsHomeFactory;

public class AutoStartup implements SmartLifecycle {

	private ParallelAppsHomeFactory parallelAppsHomeFactory;
	private Monitor monitor;

	@Inject
	public AutoStartup(
			ParallelAppsHomeFactory parallelAppsHomeFactory,
			Monitor monitor) {
		this.parallelAppsHomeFactory = parallelAppsHomeFactory;
		this.monitor = monitor;
	}

	@Override
	public int getPhase() {
		return 0;
	}

	@Override
	public boolean isAutoStartup() {
		return true;
	}

	@Override
	public boolean isRunning() {
		return running;
	}

	@Override
	public void start() {
		ContainerFactory.onStart(parallelAppsHomeFactory, monitor);
		running = true;
	}

	@Override
	public void stop() {
		running = false;
	}

	@Override
	public void stop(Runnable callback) {
		stop();
	}

	private boolean running;
}
