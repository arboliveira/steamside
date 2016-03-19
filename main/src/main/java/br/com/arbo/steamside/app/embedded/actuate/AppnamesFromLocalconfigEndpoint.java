package br.com.arbo.steamside.app.embedded.actuate;

import javax.inject.Inject;

import org.springframework.boot.actuate.endpoint.AbstractEndpoint;
import org.springframework.stereotype.Component;

import br.com.arbo.steamside.steam.client.localfiles.appcache.File_appinfo_vdf;
import br.com.arbo.steamside.steam.client.localfiles.localconfig.DumpAppNamesFrom_localconfig_vdf;
import br.com.arbo.steamside.steam.client.localfiles.localconfig.File_localconfig_vdf;

@Component
public class AppnamesFromLocalconfigEndpoint extends AbstractEndpoint<String>
{

	@Override
	public String invoke()
	{
		return new DumpAppNamesFrom_localconfig_vdf(
			file_localconfig_vdf, file_appinfo_vdf)
				.dumpToString();
	}

	@Inject
	public AppnamesFromLocalconfigEndpoint(
		File_localconfig_vdf file_localconfig_vdf,
		File_appinfo_vdf file_appinfo_vdf)
	{
		super("appnames_from_localconfig");
		this.file_localconfig_vdf = file_localconfig_vdf;
		this.file_appinfo_vdf = file_appinfo_vdf;
	}

	private final File_appinfo_vdf file_appinfo_vdf;

	private final File_localconfig_vdf file_localconfig_vdf;
}
