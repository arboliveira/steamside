package br.com.arbo.steamside.vdf.examples;

import br.com.arbo.steamside.types.AppId;
import br.com.arbo.steamside.vdf.Apps;
import br.com.arbo.steamside.vdf.Apps.AppIdVisitor;
import br.com.arbo.steamside.vdf.Factory_sharedconfig_vdf;

public class DumpSharedconfigVdfApps {

	final SysoutAppInfoLine dump;

	public DumpSharedconfigVdfApps(final SysoutAppInfoLine dump) {
		this.dump = dump;
	}

	public void dump() {
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
