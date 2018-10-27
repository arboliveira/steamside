package br.com.arbo.steamside.steam.client.internal.home;

import java.util.concurrent.CompletableFuture;
import java.util.stream.Stream;

import javax.inject.Inject;

import br.com.arbo.steamside.steam.client.apps.categories.home.AppsCategoriesHome;
import br.com.arbo.steamside.steam.client.apps.home.AppsHome;
import br.com.arbo.steamside.steam.client.categories.home.CategoriesHome;
import br.com.arbo.steamside.steam.client.home.SteamClientHome;
import br.com.arbo.steamside.steam.client.localfiles.appinfo.File_appinfo_vdf;
import br.com.arbo.steamside.steam.client.localfiles.io.Monitor;
import br.com.arbo.steamside.steam.client.localfiles.localconfig.File_localconfig_vdf;
import br.com.arbo.steamside.steam.client.localfiles.sharedconfig.File_sharedconfig_vdf;

public class SteamClientHomeFromLocalFilesAutoReload implements SteamClientHome
{

	@Override
	public AppsHome apps()
	{
		return delegate().apps();
	}

	@Override
	public AppsCategoriesHome apps_categories()
	{
		return delegate().apps_categories();
	}

	@Override
	public CategoriesHome categories()
	{
		return delegate().categories();
	}

	public void start()
	{
		reload();
		monitor.start();
	}

	public void stop()
	{
		monitor.stop();
	}

	@Inject
	public SteamClientHomeFromLocalFilesAutoReload(
		File_appinfo_vdf file_appinfo_vdf,
		File_localconfig_vdf file_localconfig_vdf,
		File_sharedconfig_vdf file_sharedconfig_vdf)
	{
		SteamClientHomeFromLocalFiles digester =
			new SteamClientHomeFromLocalFiles(
				file_appinfo_vdf,
				file_localconfig_vdf,
				file_sharedconfig_vdf);

		this.digester = digester;
		this.monitor = new Monitor(
			Stream.of(
				file_localconfig_vdf.localconfig_vdf(),
				file_sharedconfig_vdf.sharedconfig_vdf(),
				file_appinfo_vdf.appinfo_vdf()),
			this::reload);
	}

	private SteamClientHome delegate()
	{
		return getFuture().join();
	}

	private void reload()
	{
		this.setFuture(digester.hydrate());
	}

	private synchronized CompletableFuture<SteamClientHome> getFuture()
	{
		return future;
	}

	private synchronized void setFuture(
		CompletableFuture<SteamClientHome> future)
	{
		this.future = future;
	}

	private CompletableFuture<SteamClientHome> future;
	private final SteamClientHomeFromLocalFiles digester;
	private final Monitor monitor;

}
