package br.com.arbo.steamside.steam.client.localfiles.sharedconfig;

import java.io.InputStream;

import br.com.arbo.steamside.steam.client.localfiles.vdf.NotFound;
import br.com.arbo.steamside.steam.client.localfiles.vdf.Region;
import br.com.arbo.steamside.steam.client.localfiles.vdf.Vdf;

public class Parse_sharedconfig_vdf {

	public Parse_sharedconfig_vdf(InputStream in)
	{
		this.content = new Vdf(in);
	}

	public Data_sharedconfig_vdf parse()
	{
		final AppsRegion apps_region = newAppsRegion();
		final Data_sharedconfig_vdf data = apps_region.parse();
		return data;
	}

	private AppsRegion newAppsRegion()
	{
		try {
			Region rUserRoamingConfigStore =
					content.root().region("UserRoamingConfigStore");
			Region rSoftware = rUserRoamingConfigStore.region("Software");
			Region rValve = rSoftware.region("Valve");
			Region rSteam = rValve.region("Steam");
			Region rapps = rSteam.region("apps");
			return new AppsRegion(rapps);
		}
		catch (final NotFound e) {
			throw new RuntimeException(
					"Not a valid sharedconfig.vdf file?!", e);
		}
	}

	private final Vdf content;
}
