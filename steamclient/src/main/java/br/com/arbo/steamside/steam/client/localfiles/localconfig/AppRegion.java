package br.com.arbo.steamside.steam.client.localfiles.localconfig;

import br.com.arbo.steamside.steam.client.localfiles.vdf.KeyValueVisitor;
import br.com.arbo.steamside.steam.client.localfiles.vdf.Region;
import br.com.arbo.steamside.steam.client.types.LastPlayed;

class AppRegion {

	AppRegion(final Region content) {
		this.content = content;
	}

	KV_app_Impl parse()
	{
		final Hydrate hydrate = new Hydrate();
		content.accept(hydrate);
		return hydrate.app;
	}

	static class DoNothing implements KeyValueVisitor {

		@Override
		public void onKeyValue(final String k, final String v)
			throws Finished
		{
			// 
		}

		@Override
		public void onSubRegion(final String k, final Region r)
			throws Finished
		{
			skipPastEndOfRegion(r);
		}

		private void skipPastEndOfRegion(Region r)
		{
			r.accept(this);
		}

	}

	static final class Hydrate implements KeyValueVisitor {

		@Override
		public void onKeyValue(final String k, final String v)
			throws Finished
		{
			if ("LastPlayed".equalsIgnoreCase(k))
				app.lastPlayed = new LastPlayed(v);
		}

		@Override
		public void onSubRegion(final String k, final Region r)
			throws Finished
		{
			r.accept(new DoNothing());
		}

		final KV_app_Impl app = new KV_app_Impl();
	}

	private final Region content;

}