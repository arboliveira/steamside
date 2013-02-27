package br.com.arbo.steamside.steam.client.localfiles.sharedconfig;

import br.com.arbo.steamside.vdf.NotFound;
import br.com.arbo.steamside.vdf.Region;
import br.com.arbo.steamside.vdf.Vdf;

public class Parse_sharedconfig_vdf {

	public Parse_sharedconfig_vdf(final String content) {
		this.content = new Vdf(content);
	}

	public Data_sharedconfig_vdf parse() {
		final Data_sharedconfig_vdf data =
				new Data_sharedconfig_vdf();

		final Region rUserRoamingConfigStore =
				region(content.root(), "UserRoamingConfigStore");
		final Region rSoftware =
				region(rUserRoamingConfigStore, "Software");
		final Region rValve =
				region(rSoftware, "Valve");
		final Region rsteam =
				region(rValve, "Steam");
		final Region apps =
				region(rsteam, "apps");

		data.apps = new AppsRegion(apps).parse();
		return data;
	}

	private static Region region(final Region r, final String name)
	{
		try {
			return r.region(name);
		} catch (final NotFound e) {
			throw new RuntimeException("Not a valid sharedconfig.vdf file?!", e);
		}
	}

	private final Vdf content;
}
