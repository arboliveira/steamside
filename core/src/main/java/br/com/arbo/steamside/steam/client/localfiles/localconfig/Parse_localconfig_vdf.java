package br.com.arbo.steamside.steam.client.localfiles.localconfig;

import java.io.File;

import br.com.arbo.steamside.vdf.NotFound;
import br.com.arbo.steamside.vdf.Region;
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
		try {
			Region rUserRoamingConfigStore =
					content.root().region("UserLocalConfigStore");
			Region rSoftware = rUserRoamingConfigStore.region("Software");
			Region rValve = rSoftware.region("Valve");
			Region rSteam = rValve.region("Steam");
			Region rapps = rSteam.region("apps");
			return rapps;
		} catch (final NotFound e) {
			throw new RuntimeException("Not a valid localconfig.vdf file?!", e);
		}
	}

	private final Vdf content;
}
