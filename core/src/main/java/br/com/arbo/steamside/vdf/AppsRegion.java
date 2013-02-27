package br.com.arbo.steamside.vdf;

import java.util.HashMap;

import br.com.arbo.steamside.types.AppId;
import br.com.arbo.steamside.vdf.Apps.AppIdVisitor;
import br.com.arbo.steamside.vdf.Apps.AppVisitor;

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

	private AppRegion app(final AppId id) throws NotFound {
		final RegionImpl rapp = content.region(id.appid);
		return new AppRegion(rapp);
	}

	private void accept(final AppIdVisitor visitor) {
		content.accept(new KeyValueVisitor() {

			@Override
			public void onSubRegion(final String k, final RegionImpl r)
					throws Finished {
				visitor.each(new AppId(k));
			}

			@Override
			public void onKeyValue(final String k, final String v)
					throws Finished {
				// 
			}
		});
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
