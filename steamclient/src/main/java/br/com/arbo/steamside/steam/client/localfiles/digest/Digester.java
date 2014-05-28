package br.com.arbo.steamside.steam.client.localfiles.digest;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Future;

import org.apache.commons.lang3.time.StopWatch;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import br.com.arbo.steamside.steam.client.apps.AppsHome;
import br.com.arbo.steamside.steam.client.localfiles.appcache.File_appinfo_vdf;
import br.com.arbo.steamside.steam.client.localfiles.appcache.inmemory.Data_appinfo_vdf;
import br.com.arbo.steamside.steam.client.localfiles.appcache.inmemory.InMemory_appinfo_vdf;
import br.com.arbo.steamside.steam.client.localfiles.localconfig.Data_localconfig_vdf;
import br.com.arbo.steamside.steam.client.localfiles.localconfig.File_localconfig_vdf;
import br.com.arbo.steamside.steam.client.localfiles.localconfig.Parse_localconfig_vdf;
import br.com.arbo.steamside.steam.client.localfiles.sharedconfig.Data_sharedconfig_vdf;
import br.com.arbo.steamside.steam.client.localfiles.sharedconfig.File_sharedconfig_vdf;
import br.com.arbo.steamside.steam.client.localfiles.sharedconfig.Parse_sharedconfig_vdf;

public class Digester {

	public Digester(
			File_appinfo_vdf file_appinfo_vdf,
			File_localconfig_vdf file_localconfig_vdf,
			File_sharedconfig_vdf file_sharedconfig_vdf,
			ExecutorService executor)
	{
		this.file_appinfo_vdf = file_appinfo_vdf;
		this.file_localconfig_vdf = file_localconfig_vdf;
		this.file_sharedconfig_vdf = file_sharedconfig_vdf;
		this.executor = executor;
	}

	public AppsHome digest()
	{
		StopWatch w = new StopWatch();
		w.start();
		try {
			return combine().reduce();
		}
		finally {
			w.stop();
			log.info("digest: " + w.toString());
		}
	}

	Data_appinfo_vdf digest_appinfo_vdf()
	{
		StopWatch w = new StopWatch();
		w.start();
		try {
			return new InMemory_appinfo_vdf(file_appinfo_vdf);
		}
		finally {
			w.stop();
			log.info("appinfo.vdf: " + w.toString());
		}
	}

	Data_localconfig_vdf digest_localconfig_vdf() throws IOException
	{
		StopWatch w = new StopWatch();
		w.start();
		try {
			final File file = file_localconfig_vdf.localconfig_vdf();
			FileInputStream in = new FileInputStream(file);
			try {
				final Parse_localconfig_vdf parser =
						new Parse_localconfig_vdf(in);
				return parser.parse();
			}
			finally {
				in.close();
			}
		}
		finally {
			w.stop();
			log.info("localconfig.vdf: " + w.toString());
		}
	}

	Data_sharedconfig_vdf digest_sharedconfig_vdf()
			throws IOException
	{
		StopWatch w = new StopWatch();
		w.start();
		try {
			final File file = file_sharedconfig_vdf.sharedconfig_vdf();
			FileInputStream in = new FileInputStream(file);
			try {
				final Parse_sharedconfig_vdf parser =
						new Parse_sharedconfig_vdf(in);
				return parser.parse();
			}
			finally {
				in.close();
			}
		}
		finally {
			w.stop();
			log.info("sharedconfig.vdf: " + w.toString());
		}
	}

	private Combine combine()
	{
		Future<Data_localconfig_vdf> f_localconfig =
				executor.submit(this::digest_localconfig_vdf);
		Future<Data_sharedconfig_vdf> f_sharedconfig =
				executor.submit(this::digest_sharedconfig_vdf);
		Future<Data_appinfo_vdf> f_appinfo =
				executor.submit(this::digest_appinfo_vdf);
		Spread spread = new Spread(
				f_appinfo, f_localconfig, f_sharedconfig);
		try {
			return spread.converge();
		}
		catch (InterruptedException e) {
			throw new RuntimeException(e);
		}
		catch (ExecutionException e) {
			throw new RuntimeException(e);
		}
	}

	private final ExecutorService executor;

	private final File_appinfo_vdf file_appinfo_vdf;

	private final File_localconfig_vdf file_localconfig_vdf;

	private final File_sharedconfig_vdf file_sharedconfig_vdf;

	private final Log log = LogFactory.getLog(this.getClass());
}
