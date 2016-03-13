package br.com.arbo.steamside.steam.client.localfiles.appcache;

import java.util.function.Consumer;
import java.util.stream.Stream;

import br.com.arbo.steamside.out.Dump;
import br.com.arbo.steamside.out.Out;
import br.com.arbo.steamside.steam.client.localfiles.appcache.inmemory.Data_appinfo_vdf;
import br.com.arbo.steamside.steam.client.localfiles.appcache.inmemory.InMemory_appinfo_vdf;
import br.com.arbo.steamside.steam.client.localfiles.appcache.inmemory.SysoutAppInfoLine;
import br.com.arbo.steamside.steam.client.localfiles.steamlocation.SteamLocations;
import br.com.arbo.steamside.steam.client.types.AppId;

public class DumpAppNamesGivenIds
{

	private static InMemory_appinfo_vdf newAppNameFactory()
	{
		return new InMemory_appinfo_vdf(
			new File_appinfo_vdf(
				SteamLocations
					.fromSteamPhysicalFiles()));
	}

	public void dump(Consumer<String> print)
	{
		Stream<String> lines =
			SysoutAppInfoLine.lines(appids, appinfoFactory);
		new Out("ids", lines, print).out();
	}

	public String dumpToString()
	{
		return Dump.dumpToString(this::dump);
	}

	public DumpAppNamesGivenIds(String... ids)
	{
		this.appids = Stream.of(ids).map(AppId::new);
	}

	private final Stream<AppId> appids;

	private Data_appinfo_vdf appinfoFactory;

	{
		appinfoFactory = newAppNameFactory();
	}
}