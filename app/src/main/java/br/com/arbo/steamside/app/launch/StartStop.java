package br.com.arbo.steamside.app.launch;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.inject.Inject;

import org.springframework.boot.autoconfigure.EnableAutoConfiguration;

@EnableAutoConfiguration
public class StartStop {

	@PostConstruct
	public void start()
	{
		autoStartup.start();
	}

	@PreDestroy
	public void stop()
	{
		autoStartup.stop();
	}

	public @Inject AutoStartup autoStartup;

}