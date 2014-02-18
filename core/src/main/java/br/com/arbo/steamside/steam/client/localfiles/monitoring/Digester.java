package br.com.arbo.steamside.steam.client.localfiles.monitoring;

import java.io.File;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import javax.inject.Inject;

import org.springframework.context.SmartLifecycle;

import br.com.arbo.steamside.apps.AppsHome;
import br.com.arbo.steamside.steam.client.localfiles.appcache.File_appinfo_vdf;
import br.com.arbo.steamside.steam.client.localfiles.appcache.inmemory.Data_appinfo_vdf;
import br.com.arbo.steamside.steam.client.localfiles.appcache.inmemory.InMemory_appinfo_vdf;
import br.com.arbo.steamside.steam.client.localfiles.localconfig.Data_localconfig_vdf;
import br.com.arbo.steamside.steam.client.localfiles.localconfig.File_localconfig_vdf;
import br.com.arbo.steamside.steam.client.localfiles.localconfig.Parse_localconfig_vdf;
import br.com.arbo.steamside.steam.client.localfiles.sharedconfig.Data_sharedconfig_vdf;
import br.com.arbo.steamside.steam.client.localfiles.sharedconfig.File_sharedconfig_vdf;
import br.com.arbo.steamside.steam.client.localfiles.sharedconfig.Parse_sharedconfig_vdf;

public class Digester
		implements SmartLifecycle {

	@Inject
	public Digester(
			File_appinfo_vdf file_appinfo_vdf,
			File_localconfig_vdf file_localconfig_vdf,
			File_sharedconfig_vdf file_sharedconfig_vdf) {
		this.file_appinfo_vdf = file_appinfo_vdf;
		this.file_localconfig_vdf = file_localconfig_vdf;
		this.file_sharedconfig_vdf = file_sharedconfig_vdf;
	}

	private final File_localconfig_vdf file_localconfig_vdf;
	private final File_sharedconfig_vdf file_sharedconfig_vdf;
	private final File_appinfo_vdf file_appinfo_vdf;

	private boolean running;

	@Override
	public void start() {
		running = true;
	}

	@Override
	public void stop() {
		running = false;
		partsExec.shutdown();
	}

	@Override
	public boolean isRunning() {
		return running;
	}

	private final ExecutorService partsExec = Executors
			.newFixedThreadPool(3);

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

	class Digest_localconfig_vdf implements Callable<Data_localconfig_vdf> {

		@Override
		public Data_localconfig_vdf call() throws Exception {
			return digest_localconfig_vdf();
		}

	}

	Data_sharedconfig_vdf digest_sharedconfig_vdf() {
		final File file = file_sharedconfig_vdf.sharedconfig_vdf();
		final Parse_sharedconfig_vdf parser = new Parse_sharedconfig_vdf(file);
		Data_sharedconfig_vdf data = parser.parse();
		return data;
	}

	Data_localconfig_vdf digest_localconfig_vdf() {
		final File file = file_localconfig_vdf.localconfig_vdf();
		final Parse_localconfig_vdf parser = new Parse_localconfig_vdf(file);
		Data_localconfig_vdf data = parser.parse();
		return data;
	}

	Data_appinfo_vdf digest_appinfo_vdf() {
		Data_appinfo_vdf data = new InMemory_appinfo_vdf(file_appinfo_vdf);
		return data;
	}

	public AppsHome digest() {
		try {
			return digestOn();
		} catch (InterruptedException e) {
			throw new RuntimeException(e);
		} catch (ExecutionException e) {
			throw new RuntimeException(e);
		}
	}

	private AppsHome digestOn() throws InterruptedException, ExecutionException {
		Future<Data_localconfig_vdf> f_localconfig =
				partsExec.submit(new Digest_localconfig_vdf());
		Data_localconfig_vdf d_localconfig = f_localconfig.get();

		Future<Data_sharedconfig_vdf> f_sharedconfig =
				partsExec.submit(new Digest_sharedconfig_vdf());
		Data_sharedconfig_vdf d_sharedconfig = f_sharedconfig.get();

		Future<Data_appinfo_vdf> f_appinfo =
				partsExec.submit(new Digest_appinfo_vdf());
		Data_appinfo_vdf d_appinfo = f_appinfo.get();

		return new Combine(d_appinfo, d_localconfig, d_sharedconfig).combine();
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
