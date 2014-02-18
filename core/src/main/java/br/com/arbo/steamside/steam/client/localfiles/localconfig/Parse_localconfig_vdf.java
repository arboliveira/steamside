package br.com.arbo.steamside.steam.client.localfiles.localconfig;

import java.io.File;

import br.com.arbo.steamside.vdf.NotFound;
import br.com.arbo.steamside.vdf.Region;
import br.com.arbo.steamside.vdf.RegionImpl;
import br.com.arbo.steamside.vdf.Vdf;

public class Parse_localconfig_vdf {

	public Parse_localconfig_vdf(final File file) {
		this.content = new Vdf(file);
	}

	public Data_localconfig_vdf parse() {
		Region apps = findRegion_apps();
		Data_localconfig_vdf data = new AppsRegion(apps).parse();
		return data;
	}

	private Region findRegion_apps() {
		final RegionImpl rUserRoamingConfigStore =
				region(content.root(), "UserLocalConfigStore");
		final RegionImpl rSoftware =
				region(rUserRoamingConfigStore, "Software");
		final RegionImpl rValve =
				region(rSoftware, "Valve");
		final RegionImpl rsteam =
				region(rValve, "Steam");
		final Region apps =
				region(rsteam, "apps");
		return apps;
	}

	private static RegionImpl region(final RegionImpl r, final String name)
	{
		try {
			return r.region(name);
		} catch (final NotFound e) {
			throw new RuntimeException("Not a valid localconfig.vdf file?!", e);
		}
	}

	private final Vdf content;
}
