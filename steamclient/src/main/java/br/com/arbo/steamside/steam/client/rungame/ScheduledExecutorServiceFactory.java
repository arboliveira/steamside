package br.com.arbo.steamside.steam.client.rungame;

import java.util.concurrent.ScheduledExecutorService;

public interface ScheduledExecutorServiceFactory {

	ScheduledExecutorService newScheduledExecutorService(
			String prefixForThreads);
}