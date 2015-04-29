package br.com.arbo.steamside.steam.client.localfiles.sharedconfig;

import java.util.Objects;
import java.util.function.Consumer;

import br.com.arbo.steamside.steam.client.vdf.KeyValueVisitor;
import br.com.arbo.steamside.steam.client.vdf.Region;

class AppsRegion {

	AppsRegion(final Region content)
	{
		this.content = content;
	}

	Data_sharedconfig_vdf parse()
	{
		final InMemory_sharedconfig_vdf a = new InMemory_sharedconfig_vdf();
		this.forEachEntry_app(app -> a.add(app));
		return a;
	}

	private void forEachEntry_app(final Consumer<Entry_app> visitor)
	{
		class ParseEveryAppSubRegion implements KeyValueVisitor {

			@Override
			public void onKeyValue(final String k, final String v)
					throws Finished
			{
				// The "apps" region has no key/value pairs of itself.
			}

			@Override
			public void onSubRegion(final String k, final Region r)
					throws Finished
			{
				Objects.requireNonNull(k);
				final AppRegion appRegion = new AppRegion(r);
				final Entry_app app = appRegion.parse();
				app.id = k;
				visitor.accept(app);
			}
		}

		content.accept(new ParseEveryAppSubRegion());
	}

	private final Region content;

}
