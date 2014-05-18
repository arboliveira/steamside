package br.com.arbo.steamside.app.jetty;

import javax.inject.Inject;

import org.springframework.context.SmartLifecycle;

import br.com.arbo.steamside.container.ContainerFactory;
import br.com.arbo.steamside.container.ParallelAppsHomeFactory;
import br.com.arbo.steamside.data.LoadData;
import br.com.arbo.steamside.steam.client.localfiles.monitoring.Monitor;

public class AutoStartup implements SmartLifecycle {

	private ParallelAppsHomeFactory parallelAppsHomeFactory;
	private Monitor monitor;
	private LoadData loadData;

	@Inject
	public AutoStartup(
			ParallelAppsHomeFactory parallelAppsHomeFactory,
			Monitor monitor,
			LoadData loadData) {
		this.parallelAppsHomeFactory = parallelAppsHomeFactory;
		this.monitor = monitor;
		this.loadData = loadData;
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
		ContainerFactory.onStart(parallelAppsHomeFactory, monitor, loadData);
		running = true;
	}

	@Override
	public void stop() {
		ContainerFactory.onStop(parallelAppsHomeFactory, monitor);
		running = false;
	}

	@Override
	public void stop(Runnable callback) {
		stop();
		callback.run();
	}

	private boolean running;
}
