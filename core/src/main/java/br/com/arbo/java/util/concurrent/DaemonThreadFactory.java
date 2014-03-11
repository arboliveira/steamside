package br.com.arbo.java.util.concurrent;

import java.util.concurrent.ThreadFactory;

import org.apache.commons.lang3.concurrent.BasicThreadFactory;

public class DaemonThreadFactory implements ThreadFactory {

	public static DaemonThreadFactory forClass(Class< ? > aClass) {
		return withPrefix(aClass.getClass().getSimpleName());
	}

	public static DaemonThreadFactory withPrefix(String namer) {
		return new DaemonThreadFactory(namer);
	}

	private DaemonThreadFactory(final String prefix) {
		this.wrappedFactory =
				new BasicThreadFactory.Builder()
						.daemon(true)
						.namingPattern(prefix + "-%d")
						.build();
	}

	@Override
	public Thread newThread(final Runnable r) {
		return wrappedFactory.newThread(r);
	}

	private final ThreadFactory wrappedFactory;
}