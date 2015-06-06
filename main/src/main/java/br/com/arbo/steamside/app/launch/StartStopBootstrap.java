package br.com.arbo.steamside.app.launch;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.inject.Inject;

import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;

import br.com.arbo.steamside.bootstrap.BootstrapImpl;

@EnableAutoConfiguration
@ConditionalOnBean(BootstrapImpl.class)
public class StartStopBootstrap
{

	@Inject
	public StartStopBootstrap(BootstrapImpl target)
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

	private final BootstrapImpl target;

}