package br.com.arbo.steamside.steam.client.localfiles.sharedconfig;

import java.util.HashMap;
import java.util.HashSet;

import br.com.arbo.steamside.steam.client.localfiles.sharedconfig.Apps.AppVisitor;
import br.com.arbo.steamside.types.Category;
import br.com.arbo.steamside.vdf.KeyValueVisitor;
import br.com.arbo.steamside.vdf.Region;

class AppsRegion {

	private final Region content;

	AppsRegion(final Region content) {
		this.content = content;
	}

	public Apps parse() {
		final Apps a = new Apps();
		final HashMap<String, App> apps = new HashMap<String, App>();
		final HashSet<String> categories = new HashSet<String>();
		this.accept(new AppVisitor() {

			@Override
			public void each(final App app) {
				apps.put(app.appid().appid, app);
				app.accept(new Category.Visitor() {

					@Override
					public void visit(final Category each) {
						categories.add(each.category);
					}
				});
			}

		});
		a.apps = apps;
		a.categories = categories;
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
