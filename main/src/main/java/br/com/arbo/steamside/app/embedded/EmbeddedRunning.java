package br.com.arbo.steamside.app.embedded;

import org.springframework.boot.SpringApplication;
import org.springframework.context.ConfigurableApplicationContext;

import br.com.arbo.steamside.app.launch.Running;

public class EmbeddedRunning implements Running
{

	public EmbeddedRunning(ConfigurableApplicationContext context)
	{
		this.context = context;
	}

	@Override
	public void stop()
	{
		context.stop();
		SpringApplication.exit(context);
	}

	private final ConfigurableApplicationContext context;

}
