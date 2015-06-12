package br.com.arbo.steamside.app.launch;

import javax.annotation.PreDestroy;
import javax.inject.Inject;

import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextStartedEvent;

import br.com.arbo.steamside.steam.client.autoreload.ParallelAppsHomeFactory;

@EnableAutoConfiguration
@ConditionalOnBean(ParallelAppsHomeFactory.class)
public class StartStopParallelAppsHomeFactory
	implements
	ApplicationListener<ContextStartedEvent>
{

	@Inject
	public StartStopParallelAppsHomeFactory(ParallelAppsHomeFactory target)
	{
		this.target = target;
	}

	@Override
	public void onApplicationEvent(ContextStartedEvent event)
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
