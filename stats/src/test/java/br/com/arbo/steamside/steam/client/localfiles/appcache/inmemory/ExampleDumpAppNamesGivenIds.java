package br.com.arbo.steamside.steam.client.localfiles.appcache.inmemory;

import java.util.Arrays;
import java.util.stream.Stream;

import br.com.arbo.steamside.out.Out;
import br.com.arbo.steamside.steam.client.localfiles.appcache.File_appinfo_vdf;
import br.com.arbo.steamside.steam.client.localfiles.steamlocation.SteamLocations;
import br.com.arbo.steamside.steam.client.types.AppId;

class ExampleDumpAppNamesGivenIds
{

	public static void main(final String[] args)
	{
		new ExampleDumpAppNamesGivenIds().execute();
	}

	private static InMemory_appinfo_vdf newAppNameFactory()
	{
		return new InMemory_appinfo_vdf(
			new File_appinfo_vdf(
				SteamLocations
					.fromSteamPhysicalFiles()));
	}

	private void execute()
	{
		Stream<AppId> appids = Arrays.asList(
			"22000", "9050", "12800", "10150", "35460", "204560")
			.stream()
			.map(AppId::new);

		Stream<String> lines = SysoutAppInfoLine.lines(appids, appinfoFactory);
		new Out("ids", lines).out();
	}

	private Data_appinfo_vdf appinfoFactory;

	{
		appinfoFactory = newAppNameFactory();
	}
}
