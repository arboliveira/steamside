package br.com.arbo.steamside.steam.client.localfiles.digest;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Future;

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
		ExecutorService divideExecutor,
		ExecutorService conquerExecutor)
	{
		this.file_appinfo_vdf = file_appinfo_vdf;
		this.file_localconfig_vdf = file_localconfig_vdf;
		this.file_sharedconfig_vdf = file_sharedconfig_vdf;
		this.divideExecutor = divideExecutor;
		this.conquerExecutor = conquerExecutor;
	}

	public Future<AppsHome> digest()
	{
		return divideAndConquer();
	}

	Data_appinfo_vdf digest_appinfo_vdf()
	{
		return new InMemory_appinfo_vdf(file_appinfo_vdf);
	}

	Data_localconfig_vdf digest_localconfig_vdf() throws IOException
	{
		try (FileInputStream in =
			new FileInputStream(file_localconfig_vdf.localconfig_vdf());)
		{
			return new Parse_localconfig_vdf(in).parse();
		}
	}

	Data_sharedconfig_vdf digest_sharedconfig_vdf()
		throws IOException
	{
		try (FileInputStream in =
			new FileInputStream(file_sharedconfig_vdf.sharedconfig_vdf()))
		{
			return new Parse_sharedconfig_vdf(in).parse();
		}
	}

	private Future<AppsHome> divideAndConquer()
	{
		Divide divide = new Divide(
			this::digest_appinfo_vdf,
			this::digest_localconfig_vdf,
			this::digest_sharedconfig_vdf,
			divideExecutor);

		return divide.conquer(conquerExecutor);
	}

	private final ExecutorService divideExecutor;
	private final ExecutorService conquerExecutor;
	private final File_appinfo_vdf file_appinfo_vdf;
	private final File_localconfig_vdf file_localconfig_vdf;
	private final File_sharedconfig_vdf file_sharedconfig_vdf;
}
