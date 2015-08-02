package br.com.arbo.steamside.steam.client.localfiles.appcache.inmemory;

import java.util.Arrays;
import java.util.stream.Stream;

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
		final Stream<AppId> appids = Arrays.asList(
			"22000", "9050", "12800", "10150", "35460", "204560")
			.stream()
			.map(AppId::new);

		new SysoutAppInfoLine(appnameFactory)
			.forEach(appids, System.out::println);
	}

	{
		appnameFactory = newAppNameFactory();
	}

	private Data_appinfo_vdf appnameFactory;
}
