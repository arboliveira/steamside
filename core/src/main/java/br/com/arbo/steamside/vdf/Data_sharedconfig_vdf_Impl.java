package br.com.arbo.steamside.vdf;

public class Data_sharedconfig_vdf_Impl implements Data_sharedconfig_vdf {

	public Data_sharedconfig_vdf_Impl(final String content) {
		this.content = new VdfImpl(content);
	}

	@Override
	public Apps apps() {
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
		return new AppsImpl(apps);
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
