package br.com.arbo.steamside.steam.client.internal.home;

import java.util.concurrent.CompletableFuture;

import br.com.arbo.steamside.steam.client.home.SteamClientHome;
import br.com.arbo.steamside.steam.client.localfiles.appinfo.Data_appinfo_vdf;
import br.com.arbo.steamside.steam.client.localfiles.appinfo.Data_appinfo_vdf_from_File_appinfo_vdf;
import br.com.arbo.steamside.steam.client.localfiles.appinfo.File_appinfo_vdf;
import br.com.arbo.steamside.steam.client.localfiles.io.SteamLocalFileUtils;
import br.com.arbo.steamside.steam.client.localfiles.localconfig.Data_localconfig_vdf;
import br.com.arbo.steamside.steam.client.localfiles.localconfig.File_localconfig_vdf;
import br.com.arbo.steamside.steam.client.localfiles.localconfig.Parse_localconfig_vdf;
import br.com.arbo.steamside.steam.client.localfiles.sharedconfig.Data_sharedconfig_vdf;
import br.com.arbo.steamside.steam.client.localfiles.sharedconfig.File_sharedconfig_vdf;
import br.com.arbo.steamside.steam.client.localfiles.sharedconfig.Parse_sharedconfig_vdf;

public class SteamClientHomeFromLocalFiles
{

	public CompletableFuture<SteamClientHome> hydrate()
	{
		CompletableFuture<Data_appinfo_vdf> f_appinfo =
			CompletableFuture.supplyAsync(this::digest_appinfo_vdf);

		CompletableFuture<Data_localconfig_vdf> f_localconfig =
			CompletableFuture.supplyAsync(this::digest_localconfig_vdf);

		CompletableFuture<Data_sharedconfig_vdf> f_sharedconfig =
			CompletableFuture.supplyAsync(this::digest_sharedconfig_vdf);

		return CompletableFuture.allOf(
			f_appinfo, f_localconfig, f_sharedconfig)
			.thenApply(
				v -> new SteamClientHomeFromLocalFilesData(
					f_appinfo.join(), f_localconfig.join(),
					f_sharedconfig.join())
						.combine());
	}

	public SteamClientHomeFromLocalFiles(
		File_appinfo_vdf file_appinfo_vdf,
		File_localconfig_vdf file_localconfig_vdf,
		File_sharedconfig_vdf file_sharedconfig_vdf)
	{
		this.file_appinfo_vdf = file_appinfo_vdf;
		this.file_localconfig_vdf = file_localconfig_vdf;
		this.file_sharedconfig_vdf = file_sharedconfig_vdf;
	}

	private Data_appinfo_vdf digest_appinfo_vdf()
	{
		return Data_appinfo_vdf_from_File_appinfo_vdf.hydrate(file_appinfo_vdf);
	}

	private Data_localconfig_vdf digest_localconfig_vdf()
	{
		return SteamLocalFileUtils.doWithFile(
			file_localconfig_vdf.localconfig_vdf(),
			in -> new Parse_localconfig_vdf(in).parse());
	}

	private Data_sharedconfig_vdf digest_sharedconfig_vdf()
	{
		return SteamLocalFileUtils.doWithFile(
			file_sharedconfig_vdf.sharedconfig_vdf(),
			in -> new Parse_sharedconfig_vdf(in).parse());
	}

	private final File_appinfo_vdf file_appinfo_vdf;
	private final File_localconfig_vdf file_localconfig_vdf;
	private final File_sharedconfig_vdf file_sharedconfig_vdf;
}
