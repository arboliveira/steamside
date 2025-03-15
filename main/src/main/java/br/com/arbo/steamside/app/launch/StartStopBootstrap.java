package br.com.arbo.steamside.app.launch;

import jakarta.annotation.PreDestroy;
import javax.inject.Inject;

import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextStartedEvent;

import br.com.arbo.steamside.bootstrap.BootstrapImpl;

@EnableAutoConfiguration
@ConditionalOnBean(BootstrapImpl.class)
public class StartStopBootstrap
	implements
	ApplicationListener<ContextStartedEvent>
{

	@Inject
	public StartStopBootstrap(BootstrapImpl target)
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

	private final BootstrapImpl target;

}