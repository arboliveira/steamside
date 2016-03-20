package br.com.arbo.steamside.app.embedded.actuate;

import javax.inject.Inject;

import org.springframework.boot.actuate.endpoint.AbstractEndpoint;
import org.springframework.stereotype.Component;

import br.com.arbo.steamside.steam.client.localfiles.localconfig.DumpVdfStructureFrom_localconfig_vdf;

@Component
public class VdfStructureFromLocalconfigEndpoint
	extends AbstractEndpoint<String>
{

	@Override
	public String invoke()
	{
		return dumpVdfStructureFrom_localconfig_vdf.dumpToString();
	}

	@Inject
	public VdfStructureFromLocalconfigEndpoint(
		DumpVdfStructureFrom_localconfig_vdf dumpVdfStructureFrom_localconfig_vdf)
	{
		super("vdfstructure_from_localconfig");
		this.dumpVdfStructureFrom_localconfig_vdf =
			dumpVdfStructureFrom_localconfig_vdf;
	}

	private final DumpVdfStructureFrom_localconfig_vdf dumpVdfStructureFrom_localconfig_vdf;

}
