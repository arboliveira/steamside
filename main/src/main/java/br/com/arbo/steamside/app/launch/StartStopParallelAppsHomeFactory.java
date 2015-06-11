package br.com.arbo.steamside.app.launch;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.inject.Inject;

import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;

import br.com.arbo.steamside.steam.client.autoreload.ParallelAppsHomeFactory;

@EnableAutoConfiguration
@ConditionalOnBean(ParallelAppsHomeFactory.class)
public class StartStopParallelAppsHomeFactory
{

	@Inject
	public StartStopParallelAppsHomeFactory(ParallelAppsHomeFactory target)
	{
		this.target = target;
	}

	@PostConstruct
	public void start()
	{
		target.start();
	}

	@PreDestroy
	public void stop()
	{
		target.stop();
	}

	private final ParallelAppsHomeFactory target;
}