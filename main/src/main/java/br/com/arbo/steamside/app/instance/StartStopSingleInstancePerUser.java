package br.com.arbo.steamside.app.instance;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.inject.Inject;

public class StartStopSingleInstancePerUser
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
	public SingleInstancePerUser target;

}
