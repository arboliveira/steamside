package br.com.arbo.steamside.app.launch;

import jakarta.annotation.PreDestroy;
import javax.inject.Inject;

import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextStartedEvent;

import br.com.arbo.steamside.steam.client.internal.home.SteamClientHomeFromLocalFilesAutoReload;

@EnableAutoConfiguration
@ConditionalOnBean(SteamClientHomeFromLocalFilesAutoReload.class)
public class StartStopSteamClientHomeFromLocalFilesAutoReload
	implements ApplicationListener<ContextStartedEvent>
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
	public StartStopSteamClientHomeFromLocalFilesAutoReload(
		SteamClientHomeFromLocalFilesAutoReload target)
	{
		this.target = target;
	}

	private final SteamClientHomeFromLocalFilesAutoReload target;
}
