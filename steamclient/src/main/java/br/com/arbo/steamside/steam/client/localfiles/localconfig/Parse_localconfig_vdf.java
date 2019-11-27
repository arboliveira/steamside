package br.com.arbo.steamside.steam.client.localfiles.localconfig;

import java.io.InputStream;
import java.util.Optional;

import br.com.arbo.steamside.steam.client.localfiles.vdf.Candidate;
import br.com.arbo.steamside.steam.client.localfiles.vdf.KeyValueVisitor;
import br.com.arbo.steamside.steam.client.localfiles.vdf.Region;
import br.com.arbo.steamside.steam.client.localfiles.vdf.VdfStructure;

public class Parse_localconfig_vdf
{

	interface Parse_localconfig_vdf_Visitor
	{

		void on_App(KV_app app);

		void on_Appticket(KV_appticket appticket);
	}

	static class UserLocalConfigStoreVisitor implements KeyValueVisitor
	{

		private final Parse_localconfig_vdf_Visitor visitor;

		public UserLocalConfigStoreVisitor(
			Parse_localconfig_vdf_Visitor visitor)
		{
			this.visitor = visitor;
		}

		@Override
		public void onKeyValue(String k, String v) throws Finished
		{
			// Do nothing
		}

		@Override
		public void onSubRegion(String k, Region r) throws Finished
		{
			Candidate c = new Candidate(k, path);

			if (c.named("apptickets").under("UserLocalConfigStore").matches())
			{
				if (WHY_APPTICKETS) parse_apptickets(r);
				return;
			}

			if (c.named("apps")
				.under("UserLocalConfigStore/Software/Valve/Steam")
				.matches())
			{
				parse_apps(r);
				return;
			}

			final String pathbefore = path;
			path += "/" + k;
			r.accept(this);
			path = pathbefore;
		}

		void parse_apps(Region r_apps)
		{
			new AppsRegion(r_apps).accept(visitor::on_App);
		}

		void parse_apptickets(Region r_apptickets)
		{
			new AppticketsRegion(r_apptickets).accept(visitor::on_Appticket);
		}

		String path = "UserLocalConfigStore";
	}

	public static final boolean WHY_APPTICKETS = true;

	public Data_localconfig_vdf parse()
	{
		InMemory_localconfig_vdf data = new InMemory_localconfig_vdf();

		Parse_localconfig_vdf_Visitor visitor =
			new Parse_localconfig_vdf_Visitor()
			{

				@Override
				public void on_App(KV_app app)
				{
					data.apps().add(app);
				}

				@Override
				public void on_Appticket(KV_appticket appticket)
				{
					data.apptickets().add(appticket);
				}

			};

		Optional<Region> region2 =
			content.root().region("UserLocalConfigStore");

		region2
			.map(region -> visitRegion_UserLocalConfigStore(visitor, region))
			.orElseThrow(
				() -> new RuntimeException(
					"Not a valid localconfig.vdf file?!"));

		return data;
	}

	private static Object visitRegion_UserLocalConfigStore(
		Parse_localconfig_vdf_Visitor visitor, Region region)
	{
		region.accept(new UserLocalConfigStoreVisitor(visitor));

		return Boolean.TRUE;
	}

	public Parse_localconfig_vdf(final InputStream in)
	{
		this.content = new VdfStructure(in);
	}

	private final VdfStructure content;

}
