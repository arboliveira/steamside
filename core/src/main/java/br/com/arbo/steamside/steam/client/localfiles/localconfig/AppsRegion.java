package br.com.arbo.steamside.steam.client.localfiles.localconfig;

import br.com.arbo.steamside.types.AppId;
import br.com.arbo.steamside.vdf.KeyValueVisitor;
import br.com.arbo.steamside.vdf.Region;

class AppsRegion {

	private final Region content;

	AppsRegion(final Region content) {
		this.content = content;
	}

	public Data_localconfig_vdf parse() {
		final Data_localconfig_vdf data = new Data_localconfig_vdf();

		class ParseEveryAppSubRegion implements KeyValueVisitor {

			@Override
			public void onSubRegion(final String k, final Region r)
					throws Finished {
				final AppRegion appRegion = new AppRegion(r);
				final KV_app_Impl app = appRegion.parse();
				if (k == null) throw new NullPointerException();
				app.appid = new AppId(k);
				data.add(app);
			}

			@Override
			public void onKeyValue(final String k, final String v)
					throws Finished {
				// Nothing important at the other key-value pairs
			}
		}

		this.content.accept(new ParseEveryAppSubRegion());

		return data;
	}

}
