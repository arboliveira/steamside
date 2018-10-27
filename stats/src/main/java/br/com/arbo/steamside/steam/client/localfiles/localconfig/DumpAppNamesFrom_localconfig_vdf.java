package br.com.arbo.steamside.steam.client.localfiles.localconfig;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.function.Consumer;
import java.util.stream.Stream;

import javax.inject.Inject;

import br.com.arbo.steamside.out.Dump;
import br.com.arbo.steamside.out.Out;
import br.com.arbo.steamside.steam.client.localfiles.appcache.inmemory.SysoutAppInfoLine;
import br.com.arbo.steamside.steam.client.localfiles.appinfo.Data_appinfo_vdf;
import br.com.arbo.steamside.steam.client.localfiles.appinfo.Data_appinfo_vdf_from_File_appinfo_vdf;
import br.com.arbo.steamside.steam.client.localfiles.appinfo.File_appinfo_vdf;
import br.com.arbo.steamside.steam.client.types.AppId;

public class DumpAppNamesFrom_localconfig_vdf
{

	public void dump(Consumer<String> print)
	{
		this.appinfoFactory = newAppNameFactory();

		Data_localconfig_vdf data = data(file_localconfig_vdf);

		dump("apps", data.apps().streamAppId(), print);
		dump("apptickets", data.apptickets().streamAppId(), print);
	}

	public String dumpToString()
	{
		return Dump.dumpToString(this::dump);
	}

	@Inject
	public DumpAppNamesFrom_localconfig_vdf(
		File_localconfig_vdf file_localconfig_vdf,
		File_appinfo_vdf file_appinfo_vdf)
	{
		this.file_localconfig_vdf = file_localconfig_vdf;
		this.file_appinfo_vdf = file_appinfo_vdf;
	}

	private void dump(String banner, final Stream<AppId> appids,
		Consumer<String> print)
	{
		Stream<String> lines =
			SysoutAppInfoLine.lines(appids, appinfoFactory);
		new Out(banner, lines, print).out();
	}

	private Data_appinfo_vdf newAppNameFactory()
	{
		return Data_appinfo_vdf_from_File_appinfo_vdf.hydrate(file_appinfo_vdf);
	}

	private static Data_localconfig_vdf data(File_localconfig_vdf vdf)
	{
		File file = vdf.localconfig_vdf();
		try (FileInputStream in = new FileInputStream(file))
		{
			Parse_localconfig_vdf parser = new Parse_localconfig_vdf(in);
			return parser.parse();
		}
		catch (IOException e)
		{
			throw new RuntimeException(e);
		}
	}

	private Data_appinfo_vdf appinfoFactory;

	private final File_appinfo_vdf file_appinfo_vdf;

	private final File_localconfig_vdf file_localconfig_vdf;
}