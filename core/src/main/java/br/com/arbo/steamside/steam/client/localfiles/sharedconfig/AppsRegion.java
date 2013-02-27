package br.com.arbo.steamside.steam.client.localfiles.sharedconfig;

import java.util.HashMap;

import br.com.arbo.steamside.steam.client.localfiles.sharedconfig.Apps.AppVisitor;
import br.com.arbo.steamside.types.AppId;
import br.com.arbo.steamside.vdf.KeyValueVisitor;
import br.com.arbo.steamside.vdf.RegionImpl;

class AppsRegion {

	private final RegionImpl content;

	AppsRegion(final RegionImpl content) {
		this.content = content;
	}

	public Apps parse() {
		final Apps a = new Apps();
		final HashMap<String, App> apps = new HashMap<String, App>();
		this.accept(new AppVisitor() {

			@Override
			public void each(final App app) {
				apps.put(app.appid.appid, app);
			}

		});
		a.apps = apps;
		return a;
	}

	private void accept(final AppVisitor visitor) {
		class ParseEveryAppSubRegion implements KeyValueVisitor {

			@Override
			public void onSubRegion(final String k, final RegionImpl r)
					throws Finished {
				final AppRegion appRegion = new AppRegion(r);
				final App app = appRegion.parse();
				app.appid = new AppId(k);
				visitor.each(app);
			}

			@Override
			public void onKeyValue(final String k, final String v)
					throws Finished {
				// The "apps" region has no key/value pairs of itself.
			}
		}

		content.accept(new ParseEveryAppSubRegion());
	}

}
