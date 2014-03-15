package br.com.arbo.steamside.steam.client.localfiles.digest;

import java.io.File;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.function.Supplier;

import javax.inject.Inject;

import br.com.arbo.java.util.concurrent.DaemonThreadFactory;
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

public class Digester implements Supplier<AppsHome> {

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

	@Override
	public AppsHome get() {
		return combine().reduce();
	}

	private Combine combine() {
		ExecutorService executor = newThreeDaemonThreads();
		try {
			Future<Data_localconfig_vdf> f_localconfig = executor
					.submit(new Digest_localconfig_vdf());
			Future<Data_sharedconfig_vdf> f_sharedconfig = executor
					.submit(new Digest_sharedconfig_vdf());
			Future<Data_appinfo_vdf> f_appinfo = executor
					.submit(new Digest_appinfo_vdf());
			Spread spread = new Spread(
					f_appinfo, f_localconfig, f_sharedconfig);
			return spread.converge();
		} catch (InterruptedException e) {
			throw new RuntimeException(e);
		} catch (ExecutionException e) {
			throw new RuntimeException(e);
		} finally {
			executor.shutdown();
		}
	}

	private ExecutorService newThreeDaemonThreads() {
		return Executors.newFixedThreadPool(
				3, DaemonThreadFactory.forClass(this.getClass()));
	}
}
