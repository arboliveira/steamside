package br.com.arbo.steamside.app.embedded.actuate;

import javax.inject.Inject;

import org.springframework.boot.actuate.endpoint.AbstractEndpoint;
import org.springframework.stereotype.Component;

import br.com.arbo.steamside.steam.client.localfiles.appcache.File_appinfo_vdf;
import br.com.arbo.steamside.steam.client.localfiles.sharedconfig.DumpAppNamesFrom_sharedconfig_vdf;
import br.com.arbo.steamside.steam.client.localfiles.sharedconfig.File_sharedconfig_vdf;

@Component
public class AppnamesFromSharedconfigEndpoint extends AbstractEndpoint<String>
{

	@Override
	public String invoke()
	{
		return new DumpAppNamesFrom_sharedconfig_vdf(
			file_sharedconfig_vdf, file_appinfo_vdf)
				.dumpToString();
	}

	@Inject
	public AppnamesFromSharedconfigEndpoint(
		File_sharedconfig_vdf file_sharedconfig_vdf,
		File_appinfo_vdf file_appinfo_vdf)
	{
		super("appnames_from_sharedconfig");
		this.file_sharedconfig_vdf = file_sharedconfig_vdf;
		this.file_appinfo_vdf = file_appinfo_vdf;
	}

	private final File_appinfo_vdf file_appinfo_vdf;

	private final File_sharedconfig_vdf file_sharedconfig_vdf;
}
