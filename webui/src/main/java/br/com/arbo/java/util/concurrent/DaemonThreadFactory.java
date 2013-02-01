package br.com.arbo.java.util.concurrent;

import java.util.concurrent.Executors;
import java.util.concurrent.ThreadFactory;

public class DaemonThreadFactory implements ThreadFactory {

	final ThreadFactory defaultFactory =
			Executors.defaultThreadFactory();
	private final Object namer;

	public DaemonThreadFactory(final Object namer) {
		this.namer = namer;
	}

	@Override
	public Thread newThread(final Runnable r) {
		final Thread thread =
				defaultFactory.newThread(r);
		thread.setName(namer.getClass().getSimpleName() +
				"-"
				+ thread.getName());
		thread.setDaemon(true);
		return thread;
	}
}