package br.com.arbo.steamside.webui.wicket;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import br.com.arbo.java.util.concurrent.DaemonThreadFactory;
import br.com.arbo.steamside.types.AppId;
import br.com.arbo.steamside.vdf.Apps.AppIdVisitor;
import br.com.arbo.steamside.vdf.SharedconfigVdfLocation;

public class SharedConfigConsume {

	SharedConfigConsume() {
		this.executor = Executors.newFixedThreadPool(1,
				new DaemonThreadFactory(this));
		consume();
	}

	public void consume() {
		this.state = this.executor.submit(new Consume());
	}

	class Consume implements Callable<State> {

		@Override
		public State call() throws Exception {
			final State state = new State();
			SharedconfigVdfLocation.make().apps().accept(state);
			return state;
		}

	}

	class State implements AppIdVisitor {

		int number;

		@Override
		public void each(final AppId app) {
			number++;
		}
	}

	public int number() {
		return state().number;
	}

	private State state() {
		try {
			return state.get();
		} catch (final InterruptedException e) {
			throw new RuntimeException(e);
		} catch (final ExecutionException e) {
			final Throwable cause = e.getCause();
			if (cause instanceof RuntimeException)
				throw (RuntimeException) cause;
			throw new RuntimeException(e);
		}
	}

	private final ExecutorService executor;
	private Future<State> state;

}
