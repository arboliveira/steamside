package br.com.arbo.steamside.vdf;

import br.com.arbo.steamside.apps.Apps;
import br.com.arbo.steamside.apps.Apps.AppIdVisitor;
import br.com.arbo.steamside.steam.client.localfiles.sharedconfig.Factory_sharedconfig_vdf;
import br.com.arbo.steamside.types.AppId;

class Dump_sharedconfig_vdf_Apps {

	final SysoutAppInfoLine dump;

	Dump_sharedconfig_vdf_Apps(final SysoutAppInfoLine dump) {
		this.dump = dump;
	}

	void dump() {
		final Apps apps = Factory_sharedconfig_vdf.fromFile().apps();
		apps.accept(new DumpOneAppVisitor());
	}

	final class DumpOneAppVisitor implements AppIdVisitor {

		@Override
		public void each(final AppId app) {
			System.out.println(dump.toInfo(app));
		}
	}

}
