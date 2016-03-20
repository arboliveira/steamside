package br.com.arbo.steamside.app.embedded.actuate;

import javax.inject.Inject;

import org.springframework.boot.actuate.endpoint.AbstractEndpoint;
import org.springframework.stereotype.Component;

import br.com.arbo.steamside.steam.client.localfiles.sharedconfig.DumpAppNamesFrom_sharedconfig_vdf;

@Component
public class AppnamesFromSharedconfigEndpoint extends AbstractEndpoint<String>
{

	@Override
	public String invoke()
	{
		return dumpAppNamesFrom_sharedconfig_vdf.dumpToString();
	}

	@Inject
	public AppnamesFromSharedconfigEndpoint(
		DumpAppNamesFrom_sharedconfig_vdf dumpAppNamesFrom_sharedconfig_vdf)
	{
		super("appnames_from_sharedconfig");
		this.dumpAppNamesFrom_sharedconfig_vdf =
			dumpAppNamesFrom_sharedconfig_vdf;
	}

	private final DumpAppNamesFrom_sharedconfig_vdf dumpAppNamesFrom_sharedconfig_vdf;

}
