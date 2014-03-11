package br.com.arbo.steamside.steam.client.localfiles.monitoring;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import javax.inject.Inject;

import br.com.arbo.java.util.concurrent.DaemonThreadFactory;
import br.com.arbo.java.util.concurrent.FutureUtils;
import br.com.arbo.steamside.apps.AppsHome;
import br.com.arbo.steamside.apps.AppsHomeFactory;

public class AutoreloadingAppsHomeFactory implements AppsHomeFactory {

	@Override
	public AppsHome get() {
		return FutureUtils.get(state);
	}

	@Inject
	public AutoreloadingAppsHomeFactory(Digester digester) {
		this.digester = digester;
		this.executor = newSingleDaemonThread();
		reload();
	}

	void reload() {
		this.state = this.executor.submit(new Digest());
	}

	class Digest implements Callable<AppsHome> {

		@Override
		public AppsHome call() {
			return digestXT();
		}
	}

	AppsHome digestXT() {
		return digester.digest();
	}

	private ExecutorService newSingleDaemonThread() {
		return Executors.newFixedThreadPool(
				1, DaemonThreadFactory.forClass(this.getClass()));
	}

	private Future<AppsHome> state;

	private final Digester digester;

	private final ExecutorService executor;

}
