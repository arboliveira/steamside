package br.com.arbo.steamside.app.instance;

import javax.annotation.PreDestroy;
import javax.inject.Inject;

import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextStartedEvent;

public class StartStopSingleInstancePerUser
	implements
	ApplicationListener<ContextStartedEvent>
{

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

	@Inject
	public SingleInstancePerUser target;

}
