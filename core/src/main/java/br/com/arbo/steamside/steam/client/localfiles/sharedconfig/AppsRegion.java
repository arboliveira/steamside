package br.com.arbo.steamside.steam.client.localfiles.sharedconfig;

import java.util.function.Consumer;

import br.com.arbo.steamside.types.AppId;
import br.com.arbo.steamside.vdf.KeyValueVisitor;
import br.com.arbo.steamside.vdf.Region;

class AppsRegion {

	private final Region content;

	AppsRegion(final Region content) {
		this.content = content;
	}

	public void accept(final Consumer<AppId> visitor) {
		class ParseEveryAppSubRegion implements KeyValueVisitor {

			@Override
			public void onSubRegion(final String k, final Region r)
					throws Finished {
				if (k == null) throw new NullPointerException();
				visitor.accept(new AppId(k));
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
		this.forEachEntry_app(app -> a.add(app));
		return a;
	}

	private void forEachEntry_app(final Consumer<Entry_app> visitor) {
		class ParseEveryAppSubRegion implements KeyValueVisitor {

			@Override
			public void onSubRegion(final String k, final Region r)
					throws Finished {
				final AppRegion appRegion = new AppRegion(r);
				final Entry_app app = appRegion.parse();
				if (k == null) throw new NullPointerException();
				app.id = k;
				visitor.accept(app);
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
