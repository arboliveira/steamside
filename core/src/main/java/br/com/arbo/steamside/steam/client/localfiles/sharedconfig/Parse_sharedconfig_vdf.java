package br.com.arbo.steamside.steam.client.localfiles.sharedconfig;

import java.io.Closeable;
import java.io.File;
import java.io.IOException;

import br.com.arbo.steamside.vdf.NotFound;
import br.com.arbo.steamside.vdf.Region;
import br.com.arbo.steamside.vdf.RegionImpl;
import br.com.arbo.steamside.vdf.Vdf;

public class Parse_sharedconfig_vdf implements Closeable {

	public Parse_sharedconfig_vdf(File file) throws IOException {
		this.content = new Vdf(file);
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

	@Override
	public void close() throws IOException {
		content.close();
	}
}
