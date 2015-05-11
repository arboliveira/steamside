package br.com.arbo.steamside.app.launch;

import javax.inject.Inject;

import br.com.arbo.steamside.container.ParallelAppsHomeFactory;
import br.com.arbo.steamside.data.autowire.Autowire;
import br.com.arbo.steamside.steam.client.localfiles.monitoring.Monitor;

public class AutoStartup {

	@Inject
	public AutoStartup(
		ParallelAppsHomeFactory parallelAppsHomeFactory,
		Monitor monitor,
		Autowire loadData)
	{
		this.parallelAppsHomeFactory = parallelAppsHomeFactory;
		this.monitor = monitor;
		this.loadData = loadData;
	}

	public void start()
	{
		parallelAppsHomeFactory.start();
		monitor.start();
		loadData.start();
	}

	public void stop()
	{
		monitor.stop();
		parallelAppsHomeFactory.stop();
	}

	private ParallelAppsHomeFactory parallelAppsHomeFactory;
	private Monitor monitor;
	private Autowire loadData;
}
