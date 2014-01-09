package br.com.arbo.steamside.vdf;

import br.com.arbo.steamside.apps.Apps;
import br.com.arbo.steamside.apps.Apps.AppIdVisitor;
import br.com.arbo.steamside.steam.client.localfiles.sharedconfig.Factory_sharedconfig_vdf;
import br.com.arbo.steamside.types.AppId;

class Dump_sharedconfig_vdf_Apps {

	final SysoutAppInfoLine dump;
	private final Factory_sharedconfig_vdf factory_sharedconfig_vdf;

	Dump_sharedconfig_vdf_Apps(final SysoutAppInfoLine dump,
			final Factory_sharedconfig_vdf factory_sharedconfig_vdf) {
		this.dump = dump;
		this.factory_sharedconfig_vdf = factory_sharedconfig_vdf;
	}

	void dump() {
		final Apps apps = factory_sharedconfig_vdf.data().apps();
		apps.accept(new DumpOneAppVisitor());
	}

	final class DumpOneAppVisitor implements AppIdVisitor {

		@Override
		public void each(final AppId app) {
			System.out.println(dump.toInfo(app));
		}
	}

}
