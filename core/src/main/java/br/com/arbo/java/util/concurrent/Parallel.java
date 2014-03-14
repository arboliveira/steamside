package br.com.arbo.java.util.concurrent;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.function.Supplier;

public class Parallel<T> implements Supplier<T> {

	private final Supplier<T> loader;

	@Override
	public T get() {
		return FutureUtils.get(state);
	}

	public Parallel(Supplier<T> loader) {
		this.loader = loader;
		this.executor = newSingleDaemonThread();
		submit();
	}

	public void submit() {
		this.state = this.executor.submit(loader::get);
	}

	private ExecutorService newSingleDaemonThread() {
		return Executors.newFixedThreadPool(
				1, DaemonThreadFactory.forClass(this.getClass()));
	}

	private Future<T> state;

	private final ExecutorService executor;
}