package br.com.arbo.steamside.steam.client.localfiles.appcache.inmemory;

import java.util.Arrays;
import java.util.stream.Stream;

import br.com.arbo.steamside.steam.client.localfiles.appcache.File_appinfo_vdf;
import br.com.arbo.steamside.steam.client.localfiles.steamlocation.SteamLocations;
import br.com.arbo.steamside.steam.client.types.AppId;

class ExampleDumpAppNamesGivenIds {

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

	{
		appnameFactory = newAppNameFactory();
	}

	private void execute()
	{
		final Stream<AppId> appids = Arrays.asList(
				"22000", "9050", "12800", "10150", "35460", "204560"
				)
				.stream()
				.map(AppId::new);

		appids.map(
				this::toInfo
				).parallel().forEach(
						System.out::println
				);
	}

	private String toInfo(AppId appid)
	{
		return SysoutAppInfoLine.toInfo(
				new AppInfoAppNameType(
						appid, appnameFactory));
	}

	private Data_appinfo_vdf appnameFactory;
}
