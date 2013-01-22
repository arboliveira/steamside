package br.com.arbo.steamside.vdf;

public class SharedConfig {

	public SharedConfig(String content) {
		this.content = new Vdf(content);
	}

	public Apps apps() {
		Region rUserRoamingConfigStore =
				content.region("UserRoamingConfigStore");
		Region rSoftware =
				rUserRoamingConfigStore.region("Software");
		Region rValve =
				rSoftware.region("Valve");
		Region rsteam =
				rValve.region("steam");
		Region apps =
				rsteam.region("apps");
		return new Apps(apps);
	}

	private final Vdf content;
}
