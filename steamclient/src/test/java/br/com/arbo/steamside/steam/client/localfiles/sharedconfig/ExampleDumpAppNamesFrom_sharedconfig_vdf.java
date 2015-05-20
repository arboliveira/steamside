package br.com.arbo.steamside.steam.client.localfiles.sharedconfig;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.stream.Stream;

import br.com.arbo.steamside.steam.client.localfiles.appcache.File_appinfo_vdf;
import br.com.arbo.steamside.steam.client.localfiles.appcache.inmemory.AppInfoAppNameType;
import br.com.arbo.steamside.steam.client.localfiles.appcache.inmemory.Data_appinfo_vdf;
import br.com.arbo.steamside.steam.client.localfiles.appcache.inmemory.InMemory_appinfo_vdf;
import br.com.arbo.steamside.steam.client.localfiles.appcache.inmemory.SysoutAppInfoLine;
import br.com.arbo.steamside.steam.client.localfiles.steamlocation.SteamLocations;
import br.com.arbo.steamside.steam.client.types.AppId;

class ExampleDumpAppNamesFrom_sharedconfig_vdf {

	public static void main(final String[] args) throws IOException
	{
		new ExampleDumpAppNamesFrom_sharedconfig_vdf().execute();
	}

	private static Data_sharedconfig_vdf data(
			File_sharedconfig_vdf file_sharedconfig_vdf)
			throws FileNotFoundException, IOException
	{
		final File file = file_sharedconfig_vdf.sharedconfig_vdf();
		FileInputStream in = new FileInputStream(file);
		try
		{
			final Parse_sharedconfig_vdf parser = new Parse_sharedconfig_vdf(in);
			return parser.parse();
		}
		finally
		{
			in.close();
		}
	}

	private static Data_appinfo_vdf newAppNameFactory()
	{
		return new InMemory_appinfo_vdf(new File_appinfo_vdf(
				SteamLocations
						.fromSteamPhysicalFiles()));
	}

	private void execute() throws FileNotFoundException, IOException
	{
		this.appnameFactory = newAppNameFactory();

		File_sharedconfig_vdf file_sharedconfig_vdf = new File_sharedconfig_vdf(
				Dirs_userid.fromSteamPhysicalFiles());

		Data_sharedconfig_vdf data = data(file_sharedconfig_vdf);

		final Stream<AppId> appids = data.apps().streamAppId();

		appids.map(
				this::toInfo
				).parallel().forEach(
						System.out::println
				);
	}

	private String toInfo(AppId appid)
	{
		return SysoutAppInfoLine.toInfo(
				new AppInfoAppNameType(appid, appnameFactory));
	}

	private Data_appinfo_vdf appnameFactory;

}
