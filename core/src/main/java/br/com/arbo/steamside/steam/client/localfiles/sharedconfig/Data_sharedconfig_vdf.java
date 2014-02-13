package br.com.arbo.steamside.steam.client.localfiles.sharedconfig;

import br.com.arbo.steamside.apps.Apps;
import br.com.arbo.steamside.apps.Apps.AppIdVisitor;

public class Data_sharedconfig_vdf {

	public R_apps r_apps() {
		return r_apps;
	}

	public Apps apps() {
		return apps;
	}

	Apps apps;

	R_apps r_apps = new R_apps() {

		@Override
		public void accept(AppIdVisitor visitor) {
			apps.accept(visitor);
		}
	};
}
