package br.com.arbo.steamside.vdf;

public class SharedConfig {

	public SharedConfig(final String content) {
		this.content = new Vdf(content);
	}

	public Apps apps() {
		final Region rUserRoamingConfigStore =
				region(content.root(), "UserRoamingConfigStore");
		final Region rSoftware =
				region(rUserRoamingConfigStore, "Software");
		final Region rValve =
				region(rSoftware, "Valve");
		final Region rsteam =
				region(rValve, "steam");
		final Region apps =
				region(rsteam, "apps");
		return new Apps(apps);
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
