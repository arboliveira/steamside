package br.com.arbo.steamside.vdf;

public class Parse_sharedconfig_vdf {

	public Parse_sharedconfig_vdf(final String content) {
		this.content = new VdfImpl(content);
	}

	public Data_sharedconfig_vdf_Impl parse() {
		final Data_sharedconfig_vdf_Impl data =
				new Data_sharedconfig_vdf_Impl();

		final RegionImpl rUserRoamingConfigStore =
				region(content.root(), "UserRoamingConfigStore");
		final RegionImpl rSoftware =
				region(rUserRoamingConfigStore, "Software");
		final RegionImpl rValve =
				region(rSoftware, "Valve");
		final RegionImpl rsteam =
				region(rValve, "Steam");
		final RegionImpl apps =
				region(rsteam, "apps");

		data.apps = new AppsRegion(apps).parse();
		return data;
	}

	private static RegionImpl region(final RegionImpl r, final String name)
	{
		try {
			return r.region(name);
		} catch (final NotFound e) {
			throw new RuntimeException("Not a valid sharedconfig.vdf file?!", e);
		}
	}

	private final VdfImpl content;
}
