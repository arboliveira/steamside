package br.com.arbo.steamside.steam.client.localfiles.localconfig;

import br.com.arbo.steamside.apps.App;
import br.com.arbo.steamside.apps.Apps;
import br.com.arbo.steamside.apps.Apps.AppVisitor;
import br.com.arbo.steamside.vdf.KeyValueVisitor;
import br.com.arbo.steamside.vdf.Region;

class AppsRegion {

	private final Region content;

	AppsRegion(final Region content) {
		this.content = content;
	}

	public Apps parse() {
		final Apps a = new Apps();
		this.accept(new AppVisitor() {

			@Override
			public void each(final App app) {
				a.add(app);
			}

		});
		return a;
	}

	private void accept(final AppVisitor visitor) {
		class ParseEveryAppSubRegion implements KeyValueVisitor {

			@Override
			public void onSubRegion(final String k, final Region r)
					throws Finished {
				final AppRegion appRegion = new AppRegion(r);
				final App.Builder app = appRegion.parse();
				if (k == null) throw new NullPointerException();
				app.appid(k);
				visitor.each(app.make());
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
