package br.com.arbo.steamside.steam.client.localfiles.localconfig;

import java.util.Objects;

import br.com.arbo.steamside.steam.client.localfiles.vdf.KeyValueVisitor;
import br.com.arbo.steamside.steam.client.localfiles.vdf.Region;
import br.com.arbo.steamside.steam.client.types.AppId;

class AppsRegion {

	AppsRegion(final Region content, KV_apps_Impl kv_apps)
	{
		this.content = content;
		this.kv_apps = kv_apps;
	}

	public void parse()
	{
		this.content.accept(new ParseEveryAppSubRegion());
	}

	class ParseEveryAppSubRegion implements KeyValueVisitor {

		@Override
		public void onKeyValue(final String k, final String v)
				throws Finished
		{
			// Nothing important at the other key-value pairs
		}

		@Override
		public void onSubRegion(final String k, final Region r)
				throws Finished
		{
			Objects.requireNonNull(k);
			final AppRegion appRegion = new AppRegion(r);
			final KV_app_Impl app = appRegion.parse();
			app.appid = new AppId(k);
			kv_apps.add(app);
		}
	}

	final KV_apps_Impl kv_apps;

	private final Region content;

}
