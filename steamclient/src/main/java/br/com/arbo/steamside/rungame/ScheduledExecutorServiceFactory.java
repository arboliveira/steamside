package br.com.arbo.steamside.rungame;

import java.util.concurrent.ScheduledExecutorService;

public interface ScheduledExecutorServiceFactory {

	ScheduledExecutorService newScheduledExecutorService(
			String prefixForThreads);
}