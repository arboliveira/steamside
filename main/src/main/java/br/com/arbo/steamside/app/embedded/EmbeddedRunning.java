package br.com.arbo.steamside.app.embedded;

import org.springframework.boot.SpringApplication;
import org.springframework.context.ApplicationContext;

import br.com.arbo.steamside.app.launch.Running;

public class EmbeddedRunning implements Running {

	public EmbeddedRunning(ApplicationContext context)
	{
		this.context = context;
	}

	@Override
	public void stop()
	{
		SpringApplication.exit(context);
	}

	private final ApplicationContext context;

}
