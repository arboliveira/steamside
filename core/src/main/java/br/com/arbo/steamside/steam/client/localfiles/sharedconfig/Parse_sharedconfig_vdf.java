package br.com.arbo.steamside.steam.client.localfiles.sharedconfig;

import br.com.arbo.steamside.vdf.NotFound;
import br.com.arbo.steamside.vdf.Region;
import br.com.arbo.steamside.vdf.RegionImpl;
import br.com.arbo.steamside.vdf.Vdf;

public class Parse_sharedconfig_vdf {

	public Parse_sharedconfig_vdf(final String content) {
		this.content = new Vdf(content);
	}

	public R_apps apps() {
		return newAppsRegion();
	}

	public Data_sharedconfig_vdf parse() {
		final AppsRegion apps_region = newAppsRegion();
		final Data_sharedconfig_vdf data =
				new Data_sharedconfig_vdf();
		data.apps = apps_region.parse();
		return data;
	}

	private AppsRegion newAppsRegion() {
		final RegionImpl rUserRoamingConfigStore =
				region(content.root(), "UserRoamingConfigStore");
		final RegionImpl rSoftware =
				region(rUserRoamingConfigStore, "Software");
		final RegionImpl rValve =
				region(rSoftware, "Valve");
		final RegionImpl rsteam =
				region(rValve, "Steam");
		final Region apps =
				region(rsteam, "apps");

		final AppsRegion apps_region = new AppsRegion(apps);
		return apps_region;
	}

	private static RegionImpl region(final RegionImpl r, final String name)
	{
		try {
			return r.region(name);
		} catch (final NotFound e) {
			throw new RuntimeException("Not a valid sharedconfig.vdf file?!", e);
		}
	}

	private final Vdf content;
}
