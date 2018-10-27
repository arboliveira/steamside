package br.com.arbo.steamside.steam.client.localfiles.localconfig;

import java.io.InputStream;

import br.com.arbo.steamside.steam.client.localfiles.vdf.Candidate;
import br.com.arbo.steamside.steam.client.localfiles.vdf.KeyValueVisitor;
import br.com.arbo.steamside.steam.client.localfiles.vdf.NotFound;
import br.com.arbo.steamside.steam.client.localfiles.vdf.Region;
import br.com.arbo.steamside.steam.client.localfiles.vdf.Vdf;

public class Parse_localconfig_vdf {

	public Parse_localconfig_vdf(final InputStream in)
	{
		this.content = new Vdf(in);
	}

	public Data_localconfig_vdf parse()
	{
		try
		{
			Region rUserLocalConfigStore = findRegion_UserLocalConfigStore();
			rUserLocalConfigStore.accept(new UserLocalConfigStoreVisitor());
		}
		catch (final NotFound e)
		{
			throw new RuntimeException("Not a valid localconfig.vdf file?!", e);
		}
		return data;
	}

	void parse_apps(Region r_apps)
	{
		new AppsRegion(r_apps, data.apps()).parse();
	}

	void parse_apptickets(Region r_apptickets)
	{
		new AppticketsRegion(r_apptickets, data.apptickets()).parse();
	}

	private Region findRegion_UserLocalConfigStore() throws NotFound
	{
		return content.root().region("UserLocalConfigStore");
	}

	class UserLocalConfigStoreVisitor implements KeyValueVisitor {

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
				parse_apptickets(r);
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

		String path = "UserLocalConfigStore";
	}

	private final Vdf content;

	private final InMemory_localconfig_vdf data =
			new InMemory_localconfig_vdf();

}
