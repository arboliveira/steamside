package br.com.arbo.steamside.steam.client.localfiles.appinfo;

import java.util.function.Consumer;
import java.util.stream.Stream;

import javax.inject.Inject;

import br.com.arbo.steamside.out.Dump;
import br.com.arbo.steamside.out.Out;
import br.com.arbo.steamside.steam.client.localfiles.appinfo.Data_appinfo_vdf;
import br.com.arbo.steamside.steam.client.localfiles.appinfo.Data_appinfo_vdf_from_File_appinfo_vdf;
import br.com.arbo.steamside.steam.client.localfiles.appinfo.File_appinfo_vdf;
import br.com.arbo.steamside.steam.client.types.AppId;

public class DumpAppNamesGivenIds
{

	class Ids
	{

		private final Stream<AppId> appids;

		Ids(String... ids)
		{
			this.appids = Stream.of(ids).map(AppId::new);
		}

		void dump(Consumer<String> print)
		{
			Stream<String> lines =
				SysoutAppInfoLine.lines(appids, newAppNameFactory());
			new Out("ids", lines, print).out();
		}
	}

	private final File_appinfo_vdf file_appinfo_vdf;

	@Inject
	public DumpAppNamesGivenIds(File_appinfo_vdf file_appinfo_vdf)
	{
		this.file_appinfo_vdf = file_appinfo_vdf;
	}

	public void dump(Consumer<String> print, String... ids)
	{
		new Ids(ids).dump(print);
	}

	public String dumpToString(String... ids)
	{
		return Dump.dumpToString(new Ids(ids)::dump);
	}

	private Data_appinfo_vdf newAppNameFactory()
	{
		return Data_appinfo_vdf_from_File_appinfo_vdf.hydrate(file_appinfo_vdf);
	}
}