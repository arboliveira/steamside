package br.com.arbo.steamside.app.launch;

import jakarta.annotation.PreDestroy;
import javax.inject.Inject;

import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextStartedEvent;

import br.com.arbo.steamside.data.singleton.SteamsideDataBootstrap;

@EnableAutoConfiguration
@ConditionalOnBean(SteamsideDataBootstrap.class)
public class StartStopSteamsideDataBootstrap
	implements
	ApplicationListener<ContextStartedEvent>
{

	@Inject
	public StartStopSteamsideDataBootstrap(SteamsideDataBootstrap target)
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

	private final SteamsideDataBootstrap target;

}
