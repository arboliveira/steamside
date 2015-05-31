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

	@Inject
	public BootstrapImpl target;

}