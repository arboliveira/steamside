package br.com.arbo.steamside.steam.client.localfiles.sharedconfig;

import java.io.InputStream;

import br.com.arbo.steamside.steam.client.localfiles.vdf.VdfStructure;

public class Parse_sharedconfig_vdf
{

	public Parse_sharedconfig_vdf(InputStream in)
	{
		this.vdfStructure = new VdfStructure(in);
	}

	public Data_sharedconfig_vdf parse()
	{
		final AppsRegion apps_region = newAppsRegion();
		final Data_sharedconfig_vdf data = apps_region.parse();
		return data;
	}

	private AppsRegion newAppsRegion()
	{
		return vdfStructure.root().region("UserRoamingConfigStore")
			.flatMap(r -> r.region("Software"))
			.flatMap(r -> r.region("Valve"))
			.flatMap(r -> r.region("Steam"))
			.flatMap(r -> r.region("apps"))
			.map(AppsRegion::new)
			.orElseThrow(
				() -> new RuntimeException(
					"Not a valid sharedconfig.vdf file?!"));
	}

	private final VdfStructure vdfStructure;
}
