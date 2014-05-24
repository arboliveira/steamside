package br.com.arbo.steamside.steam.client.localfiles.localconfig;

import java.io.InputStream;

import br.com.arbo.steamside.vdf.NotFound;
import br.com.arbo.steamside.vdf.Region;
import br.com.arbo.steamside.vdf.Vdf;

public class Parse_localconfig_vdf {

	public Parse_localconfig_vdf(final InputStream in)
	{
		this.content = new Vdf(in);
	}

	public Data_localconfig_vdf parse()
	{
		try {
			findRegion_UserLocalConfigStore();

			// Order is important - apptickets is before apps
			parse_apptickets();
			parse_apps();
		}
		catch (final NotFound e) {
			throw new RuntimeException("Not a valid localconfig.vdf file?!", e);
		}
		return data;
	}

	private Region findRegion_apps() throws NotFound
	{
		Region rSoftware = rUserRoamingConfigStore.region("Software");
		Region rValve = rSoftware.region("Valve");
		Region rSteam = rValve.region("Steam");
		Region rapps = rSteam.region("apps");
		return rapps;
	}

	private Region findRegion_apptickets() throws NotFound
	{
		Region rapptickets = rUserRoamingConfigStore.region("apptickets");
		return rapptickets;
	}

	private void findRegion_UserLocalConfigStore() throws NotFound
	{
		this.rUserRoamingConfigStore =
				content.root().region("UserLocalConfigStore");
	}

	private void parse_apps() throws NotFound
	{
		Region region = findRegion_apps();
		new AppsRegion(region, data.apps()).parse();
	}

	private void parse_apptickets() throws NotFound
	{
		Region region = findRegion_apptickets();
		new AppticketsRegion(region, data.apptickets()).parse();
	}

	private final Vdf content;

	private final InMemory_localconfig_vdf data =
			new InMemory_localconfig_vdf();

	private Region rUserRoamingConfigStore;
}
