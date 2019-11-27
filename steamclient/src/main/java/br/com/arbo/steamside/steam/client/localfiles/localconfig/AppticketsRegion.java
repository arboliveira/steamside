package br.com.arbo.steamside.steam.client.localfiles.localconfig;

import java.util.Objects;

import br.com.arbo.steamside.steam.client.localfiles.vdf.KeyValueVisitor;
import br.com.arbo.steamside.steam.client.localfiles.vdf.Region;
import br.com.arbo.steamside.steam.client.types.AppId;

class AppticketsRegion
{

	AppticketsRegion(Region content)
	{
		this.content = content;
	}

	public void accept(AppticketVisitor appticketVisitor)
	{
		this.content.accept(new ParseEveryAppticketKeyValue(appticketVisitor));
	}

	interface AppticketVisitor
	{

		void on_Appticket(KV_appticket appticket);
	}

	class ParseEveryAppticketKeyValue implements KeyValueVisitor
	{

		private final AppticketVisitor appticketVisitor;

		public ParseEveryAppticketKeyValue(AppticketVisitor appticketVisitor)
		{
			this.appticketVisitor = appticketVisitor;
		}

		@Override
		public void onKeyValue(final String k, final String v)
			throws Finished
		{
			KV_appticket_Impl app = new KV_appticket_Impl();
			app.appid = new AppId(Objects.requireNonNull(k));
			appticketVisitor.on_Appticket(app);
		}

		@Override
		public void onSubRegion(final String k, final Region r)
			throws Finished
		{
			// apptickets has no sub-regions
		}
	}

	private final Region content;

}
