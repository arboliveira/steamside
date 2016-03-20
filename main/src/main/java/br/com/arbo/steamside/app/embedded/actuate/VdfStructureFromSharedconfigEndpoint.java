package br.com.arbo.steamside.app.embedded.actuate;

import javax.inject.Inject;

import org.springframework.boot.actuate.endpoint.AbstractEndpoint;
import org.springframework.stereotype.Component;

import br.com.arbo.steamside.steam.client.localfiles.sharedconfig.DumpVdfStructureFrom_sharedconfig_vdf;

@Component
public class VdfStructureFromSharedconfigEndpoint
	extends AbstractEndpoint<String>
{

	@Override
	public String invoke()
	{
		return dumpVdfStructureFrom_sharedconfig_vdf.dumpToString();
	}

	@Inject
	public VdfStructureFromSharedconfigEndpoint(
		DumpVdfStructureFrom_sharedconfig_vdf dumpVdfStructureFrom_sharedconfig_vdf)
	{
		super("vdfstructure_from_sharedconfig");
		this.dumpVdfStructureFrom_sharedconfig_vdf =
			dumpVdfStructureFrom_sharedconfig_vdf;
	}

	private final DumpVdfStructureFrom_sharedconfig_vdf dumpVdfStructureFrom_sharedconfig_vdf;

}
