package br.com.arbo.steamside.app.embedded.actuate;

import javax.inject.Inject;

import org.springframework.boot.actuate.endpoint.AbstractEndpoint;
import org.springframework.stereotype.Component;

import br.com.arbo.steamside.steam.client.localfiles.sharedconfig.DumpVdfStructureFrom_sharedconfig_vdf;
import br.com.arbo.steamside.steam.client.localfiles.sharedconfig.File_sharedconfig_vdf;

@Component
public class VdfStructureFromSharedconfigEndpoint
	extends AbstractEndpoint<String>
{

	@Override
	public String invoke()
	{
		return new DumpVdfStructureFrom_sharedconfig_vdf(
			file_sharedconfig_vdf)
				.dumpToString();
	}

	@Inject
	public VdfStructureFromSharedconfigEndpoint(
		File_sharedconfig_vdf file_sharedconfig_vdf)
	{
		super("vdfstructure_from_sharedconfig");
		this.file_sharedconfig_vdf = file_sharedconfig_vdf;
	}

	private final File_sharedconfig_vdf file_sharedconfig_vdf;
}
