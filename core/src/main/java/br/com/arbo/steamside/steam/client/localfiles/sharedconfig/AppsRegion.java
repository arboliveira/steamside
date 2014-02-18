package br.com.arbo.steamside.steam.client.localfiles.sharedconfig;

import br.com.arbo.steamside.types.AppId;
import br.com.arbo.steamside.vdf.KeyValueVisitor;
import br.com.arbo.steamside.vdf.Region;

class AppsRegion {

	private final Region content;

	AppsRegion(final Region content) {
		this.content = content;
	}

	public void accept(final AppId.Visitor visitor) {
		class ParseEveryAppSubRegion implements KeyValueVisitor {

			@Override
			public void onSubRegion(final String k, final Region r)
					throws Finished {
				if (k == null) throw new NullPointerException();
				visitor.each(new AppId(k));
			}

			@Override
			public void onKeyValue(final String k, final String v)
					throws Finished {
				// The "apps" region has no key/value pairs of itself.
			}
		}

		content.accept(new ParseEveryAppSubRegion());
	}

	Data_sharedconfig_vdf parse() {
		final InMemory_sharedconfig_vdf a = new InMemory_sharedconfig_vdf();
		this.accept(new Entry_app.Visitor() {

			@Override
			public void each(final Entry_app app) {
				a.add(app);
			}

		});
		return a;
	}

	private void accept(final Entry_app.Visitor visitor) {
		class ParseEveryAppSubRegion implements KeyValueVisitor {

			@Override
			public void onSubRegion(final String k, final Region r)
					throws Finished {
				final AppRegion appRegion = new AppRegion(r);
				final Entry_app app = appRegion.parse();
				if (k == null) throw new NullPointerException();
				app.id = k;
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
