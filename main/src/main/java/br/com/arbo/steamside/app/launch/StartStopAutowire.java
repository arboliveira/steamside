package br.com.arbo.steamside.app.launch;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.inject.Inject;

import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;

@EnableAutoConfiguration
@ConditionalOnBean(AutowireConditional.class)
public class StartStopAutowire {

	@PostConstruct
	public void start()
	{
		auto.autowire.start();
	}

	@PreDestroy
	public void stop()
	{
		auto.autowire.stop();
	}

	public @Inject AutowireConditional auto;

}