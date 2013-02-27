package br.com.arbo.steamside.webui.wicket;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import br.com.arbo.java.util.concurrent.DaemonThreadFactory;
import br.com.arbo.steamside.steam.client.localfiles.sharedconfig.Data_sharedconfig_vdf;
import br.com.arbo.steamside.steam.client.localfiles.sharedconfig.Factory_sharedconfig_vdf;

public class SharedConfigConsume {

	SharedConfigConsume() {
		this.executor = Executors.newFixedThreadPool(1,
				new DaemonThreadFactory(this));
		consume();
	}

	public void consume() {
		this.state = this.executor.submit(new Consume());
	}

	class Consume implements Callable<Data_sharedconfig_vdf> {

		@Override
		public Data_sharedconfig_vdf call() throws Exception {
			return Factory_sharedconfig_vdf.fromFile();
		}

	}

	public Data_sharedconfig_vdf data() {
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
	private Future<Data_sharedconfig_vdf> state;

}
