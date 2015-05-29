package br.com.arbo.steamside.app.launch;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.inject.Inject;

import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;

import br.com.arbo.steamside.data.SteamsideDataExecutorImpl;
import br.com.arbo.steamside.steam.client.autoreload.ParallelAppsHomeFactory;

@EnableAutoConfiguration
@ConditionalOnBean(ParallelAppsHomeFactory.class)
public class StartStopParallelAppsHomeFactory
{

	@PostConstruct
	public void start()
	{
		auto.start();
		dataExecutor.start();
	}

	@PreDestroy
	public void stop()
	{
		dataExecutor.shutdown();
		auto.stop();
	}

	public @Inject ParallelAppsHomeFactory auto;

	public @Inject SteamsideDataExecutorImpl dataExecutor;
}
