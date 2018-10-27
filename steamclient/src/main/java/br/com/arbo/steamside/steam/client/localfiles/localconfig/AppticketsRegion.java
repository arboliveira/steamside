package br.com.arbo.steamside.steam.client.localfiles.localconfig;

import java.util.Objects;

import br.com.arbo.steamside.steam.client.localfiles.vdf.KeyValueVisitor;
import br.com.arbo.steamside.steam.client.localfiles.vdf.Region;
import br.com.arbo.steamside.steam.client.types.AppId;

class AppticketsRegion {

	AppticketsRegion(final Region content, KV_apptickets_Impl kv_apptickets)
	{
		this.content = content;
		this.kv_apptickets = kv_apptickets;
	}

	public void parse()
	{
		this.content.accept(new ParseEveryAppticketKeyValue());
	}

	class ParseEveryAppticketKeyValue implements KeyValueVisitor {

		@Override
		public void onKeyValue(final String k, final String v)
				throws Finished
		{
			Objects.requireNonNull(k);
			final KV_appticket_Impl app = new KV_appticket_Impl();
			app.appid = new AppId(k);
			kv_apptickets.add(app);
		}

		@Override
		public void onSubRegion(final String k, final Region r)
				throws Finished
		{
			// apptickets has no sub-regions
		}
	}

	final KV_apptickets_Impl kv_apptickets;

	private final Region content;

}
