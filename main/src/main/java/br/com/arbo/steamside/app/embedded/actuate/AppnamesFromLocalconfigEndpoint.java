package br.com.arbo.steamside.app.embedded.actuate;

import javax.inject.Inject;

import org.springframework.boot.actuate.endpoint.AbstractEndpoint;
import org.springframework.stereotype.Component;

import br.com.arbo.steamside.steam.client.localfiles.localconfig.DumpAppNamesFrom_localconfig_vdf;

@Component
public class AppnamesFromLocalconfigEndpoint extends AbstractEndpoint<String>
{

	@Override
	public String invoke()
	{
		return dumpAppNamesFrom_localconfig_vdf.dumpToString();
	}

	@Inject
	public AppnamesFromLocalconfigEndpoint(
		DumpAppNamesFrom_localconfig_vdf dumpAppNamesFrom_localconfig_vdf)
	{
		super("appnames_from_localconfig");
		this.dumpAppNamesFrom_localconfig_vdf =
			dumpAppNamesFrom_localconfig_vdf;
	}

	private final DumpAppNamesFrom_localconfig_vdf dumpAppNamesFrom_localconfig_vdf;

}
