package br.com.arbo.steamside.vdf;

import br.com.arbo.steamside.steam.client.localfiles.sharedconfig.Apps;
import br.com.arbo.steamside.steam.client.localfiles.sharedconfig.Apps.AppIdVisitor;
import br.com.arbo.steamside.steam.client.localfiles.sharedconfig.Factory_sharedconfig_vdf;
import br.com.arbo.steamside.types.AppId;

class DumpSharedconfigVdfApps {

	final SysoutAppInfoLine dump;

	DumpSharedconfigVdfApps(final SysoutAppInfoLine dump) {
		this.dump = dump;
	}

	void dump() {
		final Apps apps = Factory_sharedconfig_vdf.fromFile().apps();
		apps.accept(new DumpOneAppVisitor());
	}

	final class DumpOneAppVisitor implements AppIdVisitor {

		@Override
		public void each(final AppId app) {
			dump.sysout(app);
		}
	}

}
