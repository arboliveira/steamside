package br.com.arbo.steamside.steam.client.localfiles.monitoring;

import java.io.File;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import javax.inject.Inject;

import org.springframework.context.SmartLifecycle;

import br.com.arbo.steamside.steam.client.localfiles.appcache.File_appinfo_vdf;
import br.com.arbo.steamside.steam.client.localfiles.appcache.inmemory.Data_appinfo_vdf;
import br.com.arbo.steamside.steam.client.localfiles.appcache.inmemory.InMemory_appinfo_vdf;
import br.com.arbo.steamside.steam.client.localfiles.sharedconfig.Data_sharedconfig_vdf;
import br.com.arbo.steamside.steam.client.localfiles.sharedconfig.File_sharedconfig_vdf;
import br.com.arbo.steamside.steam.client.localfiles.sharedconfig.Parse_sharedconfig_vdf;

public class Digester
		implements SmartLifecycle, ChangeListener {

	@Inject
	private File_sharedconfig_vdf file_sharedconfig_vdf;
	@Inject
	private File_appinfo_vdf file_appinfo_vdf;

	private boolean running;

	@Override
	public void start() {
		digestInParallel();
		running = true;
	}

	@Override
	public void stop() {
		running = false;
		digestExec.shutdown();
		partsExec.shutdown();
	}

	@Override
	public boolean isRunning() {
		return running;
	}

	@Override
	public void fileChanged() {
		digestInParallel();
	}

	private void digestInParallel() {
		digestExec.execute(new Digest());
	}

	class Digest implements Runnable {

		@Override
		public void run() {
			digestXT();
		}
	}

	private final ExecutorService digestExec = Executors
			.newSingleThreadExecutor();
	private final ExecutorService partsExec = Executors
			.newFixedThreadPool(3);

	void digestXT() {
		try {
			digest();
		} catch (InterruptedException e) {
			throw new RuntimeException(e);
		} catch (ExecutionException e) {
			throw new RuntimeException(e);
		}
	}

	class Digest_sharedconfig_vdf implements Callable<Data_sharedconfig_vdf> {

		@Override
		public Data_sharedconfig_vdf call() throws Exception {
			return digest_sharedconfig_vdf();
		}

	}

	class Digest_appinfo_vdf implements Callable<Data_appinfo_vdf> {

		@Override
		public Data_appinfo_vdf call() throws Exception {
			return digest_appinfo_vdf();
		}

	}

	Data_sharedconfig_vdf digest_sharedconfig_vdf() {
		final File file = file_sharedconfig_vdf.sharedconfig_vdf();
		final Parse_sharedconfig_vdf parser = new Parse_sharedconfig_vdf(
				file);
		Data_sharedconfig_vdf data = parser.parse();
		return data;
	}

	Data_appinfo_vdf digest_appinfo_vdf() {
		Data_appinfo_vdf data = new InMemory_appinfo_vdf(file_appinfo_vdf);
		return data;
	}

	private void digest() throws InterruptedException, ExecutionException {
		Future<Data_sharedconfig_vdf> f_sharedConfig =
				partsExec.submit(new Digest_sharedconfig_vdf());
		Data_sharedconfig_vdf data = f_sharedConfig.get();
		System.out.println(data.apps().count());
	}

	@Override
	public int getPhase() {
		return 1;
	}

	@Override
	public boolean isAutoStartup() {
		return true;
	}

	@Override
	public void stop(Runnable callback) {
		stop();
		callback.run();
	}
}
