package br.com.arbo.java.util.concurrent;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.function.Supplier;

public class Parallel<T> implements Supplier<T> {

	public Parallel(Supplier<T> loader) {
		this.loader = loader;
		this.executor = newSingleDaemonThread();
	}

	@Override
	public T get() {
		return FutureUtils.get(state);
	}

	public void start() {
		this.submit();
	}

	public void stop() {
		executor.shutdown();
	}

	public void submit() {
		this.state = this.executor.submit(loader::get);
	}

	private ExecutorService newSingleDaemonThread() {
		return Executors.newFixedThreadPool(
				1, DaemonThreadFactory.forClass(this.getClass()));
	}

	private final ExecutorService executor;

	private final Supplier<T> loader;

	private Future<T> state;
}
