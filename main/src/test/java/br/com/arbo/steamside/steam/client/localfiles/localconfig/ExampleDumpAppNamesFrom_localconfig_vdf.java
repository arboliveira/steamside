package br.com.arbo.steamside.steam.client.localfiles.localconfig;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.stream.Stream;

import br.com.arbo.steamside.report.Report;
import br.com.arbo.steamside.steam.client.localfiles.appcache.File_appinfo_vdf;
import br.com.arbo.steamside.steam.client.localfiles.appcache.inmemory.Data_appinfo_vdf;
import br.com.arbo.steamside.steam.client.localfiles.appcache.inmemory.InMemory_appinfo_vdf;
import br.com.arbo.steamside.steam.client.localfiles.appcache.inmemory.SysoutAppInfoLine;
import br.com.arbo.steamside.steam.client.localfiles.sharedconfig.Dirs_userid;
import br.com.arbo.steamside.steam.client.localfiles.steamlocation.SteamLocations;
import br.com.arbo.steamside.steam.client.types.AppId;

class ExampleDumpAppNamesFrom_localconfig_vdf
{

	public static void main(final String[] args) throws IOException
	{
		new ExampleDumpAppNamesFrom_localconfig_vdf().execute();
	}

	private static Data_localconfig_vdf data(File_localconfig_vdf vdf)
		throws FileNotFoundException, IOException
	{
		final File file = vdf.localconfig_vdf();
		try (FileInputStream in = new FileInputStream(file))
		{
			final Parse_localconfig_vdf parser =
				new Parse_localconfig_vdf(in);
			return parser.parse();
		}
	}

	private static Data_appinfo_vdf newAppNameFactory()
	{
		return new InMemory_appinfo_vdf(new File_appinfo_vdf(
			SteamLocations
			.fromSteamPhysicalFiles()));
	}

	private void dump(String banner, final Stream<AppId> appids)
	{
		new Report(
			banner, new SysoutAppInfoLine(appids, appnameFactory).lines())
		.print();
	}

	private void execute() throws FileNotFoundException, IOException
	{
		this.appnameFactory = newAppNameFactory();

		File_localconfig_vdf vdf = new File_localconfig_vdf(
			Dirs_userid.fromSteamPhysicalFiles());

		Data_localconfig_vdf data = data(vdf);

		dump("apps", data.apps().streamAppId());
		dump("apptickets", data.apptickets().streamAppId());
	}

	private Data_appinfo_vdf appnameFactory;

}
