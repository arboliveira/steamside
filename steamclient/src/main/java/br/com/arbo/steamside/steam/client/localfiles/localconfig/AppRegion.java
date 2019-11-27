package br.com.arbo.steamside.steam.client.localfiles.localconfig;

import br.com.arbo.steamside.steam.client.localfiles.vdf.KeyValueVisitor;
import br.com.arbo.steamside.steam.client.localfiles.vdf.Region;

class AppRegion
{

	AppRegion(final Region content)
	{
		this.content = content;
	}

	interface AppRegionVisitor
	{

		void on_LastPlayed(String v);
	}

	void accept(AppRegionVisitor visitor)
	{
		content.accept(new Hydrate(visitor));
	}

	static class DoNothing implements KeyValueVisitor
	{

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

	static final class Hydrate implements KeyValueVisitor
	{

		private final AppRegionVisitor visitor;

		public Hydrate(AppRegionVisitor visitor)
		{
			this.visitor = visitor;
		}

		@Override
		public void onKeyValue(final String k, final String v)
			throws Finished
		{
			if ("LastPlayed".equalsIgnoreCase(k))
				visitor.on_LastPlayed(v);
		}

		@Override
		public void onSubRegion(final String k, final Region r)
			throws Finished
		{
			r.accept(new DoNothing());
		}

	}

	private final Region content;

}