package br.com.arbo.steamside.steam.client.localfiles.localconfig;

import java.util.Objects;

import br.com.arbo.steamside.steam.client.localfiles.vdf.KeyValueVisitor;
import br.com.arbo.steamside.steam.client.localfiles.vdf.Region;
import br.com.arbo.steamside.steam.client.types.AppId;
import br.com.arbo.steamside.steam.client.types.LastPlayed;

class AppsRegion
{

	AppsRegion(Region content)
	{
		this.content = content;
	}

	public void accept(AppSubRegionVisitor visitor)
	{
		this.content.accept(new ParseEveryAppSubRegion(visitor));
	}

	interface AppSubRegionVisitor
	{

		void on_App(KV_app app);
	}

	static class ParseEveryAppSubRegion implements KeyValueVisitor
	{

		private final AppSubRegionVisitor visitor;

		public ParseEveryAppSubRegion(AppSubRegionVisitor visitor)
		{
			this.visitor = visitor;
		}

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
			KV_app_Impl app = new KV_app_Impl();

			app.appid = new AppId(Objects.requireNonNull(k));

			AppRegion appRegion = new AppRegion(r);

			appRegion.accept(v -> app.lastPlayed = new LastPlayed(v));

			visitor.on_App(app);
		}
	}

	private final Region content;

}
